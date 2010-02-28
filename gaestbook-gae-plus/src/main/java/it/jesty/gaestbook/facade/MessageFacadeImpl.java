package it.jesty.gaestbook.facade;

import it.jesty.gaestbook.bean.Message;
import it.jesty.gaestbook.cache.GaestbookCacheWrapper;
import it.jesty.gaestbook.cache.MessagesCache;
import it.jesty.gaestbook.cache.UserCacheKey;
import it.jesty.gaestbook.dao.MessageDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;

@Component
public class MessageFacadeImpl implements MessageFacade {
	
	private static final String HOME_CACHE_KEY = "home";
	private final MessageDao messageDao;
	private final GaestbookCacheWrapper gaestbookCacheWrapper;

	@Autowired
	public MessageFacadeImpl(MessageDao messageDao, GaestbookCacheWrapper gaestbookCacheWrapper) {
		this.messageDao = messageDao;
		this.gaestbookCacheWrapper = gaestbookCacheWrapper;
	}

	public Message sendMessage(Message message){
		String uuid = UUID.randomUUID().toString();
		message.setUuid(uuid);
		message.setCreated(new Date());
		this.messageDao.createMessage(message);
		return message;
	}
	
	public int deleteUnconfirmed(Date date){
		return this.messageDao.deleteUnconfirmedMessage(date);
	}
	
	public void queueMail(Message message) {
		Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions taskOpt = TaskOptions.Builder
				.url("/app/tasks/sendconfirmation")
				.param("email", message.getEmail())
				.param("name", message.getName())
				.param("text", message.getText())
				.param("uuid", message.getUuid());
		queue.add(taskOpt);
	}

	public Message conifrmMessage(String uuid){
		Message message = this.messageDao.confirmMessage(uuid);
		refreshCacheFor(UserCacheKey.build(message.getName()), message);
		refreshCacheFor(HOME_CACHE_KEY, message);
		return message;
	}
	
	public Collection<Message> listMessages(){
		if(gaestbookCacheWrapper.containsKey(HOME_CACHE_KEY)){
			return ((MessagesCache) gaestbookCacheWrapper.get(HOME_CACHE_KEY)).getAll();
		} else {
			Collection<Message> list = this.messageDao.list();
			//because the returned collection is not serializable
			gaestbookCacheWrapper.put(HOME_CACHE_KEY, MessagesCache.build(list));
			return list;
		}
	}

	@Override
	public Collection<Message> listMessages(String userid) {
		UserCacheKey key = UserCacheKey.build(userid);
		if(gaestbookCacheWrapper.containsKey(key)){
			return ((MessagesCache) gaestbookCacheWrapper.get(key)).getAll();
		} else {
			Collection<Message> list = this.messageDao.list(userid);
			//because the returned collection is not serializable
			gaestbookCacheWrapper.put(key, MessagesCache.build(list));
			return list;
		}
	}
	
	private void refreshCacheFor(Serializable key, Message message){
		MessagesCache messagesCache = (MessagesCache) gaestbookCacheWrapper.get(key);
		if(messagesCache != null) {
			messagesCache.add(message);
			gaestbookCacheWrapper.put(key, messagesCache);
		}
	}
	
}

