package it.jesty.gaestbook.facade;

import it.jesty.gaestbook.bean.Message;

import java.util.Collection;

public interface MessageFacade {
	
	void sendMessage(Message message);

	Collection<Message> listMessages();

}
