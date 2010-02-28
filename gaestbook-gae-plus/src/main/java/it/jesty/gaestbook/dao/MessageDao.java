package it.jesty.gaestbook.dao;

import it.jesty.gaestbook.bean.Message;

import java.util.Collection;
import java.util.Date;

public interface MessageDao {

	Collection<Message> list();

	void createMessage(Message message);

	Message confirmMessage(String uuid);

	Message findByUUID(String uuid);

	int deleteUnconfirmedMessage(Date before);

	Collection<Message> list(String userid);

}
