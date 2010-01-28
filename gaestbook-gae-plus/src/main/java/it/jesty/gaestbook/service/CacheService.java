package it.jesty.gaestbook.service;

import it.jesty.gaestbook.bean.Message;

import java.util.Collection;

public interface CacheService {

	void put(Message message);
	
	Collection<Message> getAll();
	
	void invalidateAll();
	
	boolean isEmpty();

	void putAll(Collection<Message> list);
	
}
