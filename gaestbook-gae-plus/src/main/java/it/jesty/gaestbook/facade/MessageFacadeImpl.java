package it.jesty.gaestbook.facade;

import it.jesty.gaestbook.bean.Message;
import it.jesty.gaestbook.cache.GaestbookCacheWrapper;
import it.jesty.gaestbook.dao.MessageDao;

import java.util.ArrayList;
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
		invalidateCacheFor(message.getName());

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
		invalidateCacheFor(message.getName());
		return message;
	}
	
	public Collection<Message> listMessages(){
		return this.messageDao.list();
	}

	@Override
	public Collection<Message> listMessages(String userid) {
		//TODO: cache non funziona!!!
		if(gaestbookCacheWrapper.containsKey(userid)){
			return (Collection<Message>) gaestbookCacheWrapper.get(userid);
		} else {
			Collection<Message> list = this.messageDao.list(userid);
			//because the returned collection is not serializable
			gaestbookCacheWrapper.put(userid, new ArrayList<Message>(list));
			return list;
		}
	}
	
	private void invalidateCacheFor(String name){
		gaestbookCacheWrapper.remove(name);
	}
	
}

