package it.jesty.gaestbook.dao;

import it.jesty.gaestbook.bean.Message;

import java.util.Collection;

public interface MessageDao {

	Collection<Message> list();

	void createMessage(Message message);

}
