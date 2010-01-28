package it.jesty.gaestbook.facade;

import it.jesty.gaestbook.bean.Message;
import it.jesty.gaestbook.dao.MessageDao;
import it.jesty.gaestbook.service.CacheService;
import it.jesty.gaestbook.service.EmailService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFacadeImpl implements MessageFacade {
	
	private final MessageDao messageDao;
	private final EmailService emailService;
	private final CacheService cacheService;

	@Autowired
	public MessageFacadeImpl(MessageDao messageDao, EmailService emailService, CacheService cacheService) {
		this.messageDao = messageDao;
		this.emailService = emailService;
		this.cacheService = cacheService;
	}

	public void sendMessage(Message message){
		String uuid = UUID.randomUUID().toString();
		message.setUuid(uuid);
		this.messageDao.createMessage(message);
		this.cacheService.put(message);
		this.emailService.sendMail("davidecerbo@gmail.com", message.getEmail(), "Thanks you to leave a message on my Gaestbook!", "thanks :)");
	}
	
	public Collection<Message> listMessages(){
		Collection<Message> list = new ArrayList<Message>();
		if(cacheService.isEmpty()){
			 list = this.messageDao.list();
			 cacheService.putAll(list);
		} else {
			list = cacheService.getAll();
		}
		return list;
	}
	
}
