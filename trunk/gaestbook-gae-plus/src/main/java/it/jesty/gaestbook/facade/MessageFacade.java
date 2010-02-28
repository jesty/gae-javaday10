package it.jesty.gaestbook.facade;

import it.jesty.gaestbook.bean.Message;

import java.util.Collection;
import java.util.Date;

public interface MessageFacade {
	
	Message sendMessage(Message message);

	Collection<Message> listMessages();

	Message conifrmMessage(String uuid);

	int deleteUnconfirmed(Date date);

	Collection<Message> listMessages(String userid);

	void queueMail(Message message);

}
