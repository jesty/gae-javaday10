package it.jesty.gaestbook.facade;

import it.jesty.gaestbook.bean.Message;
import it.jesty.gaestbook.dao.MessageDao;
import it.jesty.gaestbook.service.EmailService;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFacadeImpl implements MessageFacade {
	
	private final MessageDao messageDao;
	private final EmailService emailService;

	@Autowired
	public MessageFacadeImpl(MessageDao messageDao, EmailService emailService) {
		this.messageDao = messageDao;
		this.emailService = emailService;
	}

	public void sendMessage(Message message){
		String uuid = UUID.randomUUID().toString();
		message.setUuid(uuid);
		this.messageDao.createMessage(message);
		this.emailService.sendMail("davidecerbo@gmail.com", message.getEmail(), "G(a)estbook", buildText(message));
	}
	
	public Message conifrmMessage(String uuid){
		return this.messageDao.confirmMessage(uuid);
	}
	
	public Collection<Message> listMessages(){
		return this.messageDao.list();
	}
	
	private String buildText(Message message) {
		return new StringBuilder("Hi, ")
					.append(message.getName())
					.append(" you have posted this message: \n")
					.append("\"")
					.append(message.getText())
					.append("\"\n")
					.append("Please confirm it visiting this url: ")
					.append("http://g-a-estbook.appspot.com/app/confirm/")
					.append(message.getUuid())
					.append("\nBest regardes,\n G(a)estbook")
					.toString();
	}

	
}

