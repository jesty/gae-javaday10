package it.jesty.gaestbook.service;

import it.jesty.gaestbook.bean.Message;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

import org.springframework.stereotype.Component;

@Component
public class CacheServiceImpl implements CacheService {
	
	private static final Logger log = Logger.getLogger(EmailServiceImpl.class.getName());
	
	private Cache cache;

	CacheServiceImpl(){
	    try {
	        cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
	    } catch (CacheException e) {
	    	log.severe("Error during the creation of the cache. Error: " + e.getMessage());
	    }
	}

	@Override
	public Collection<Message> getAll() {
		return cache.values();
	}

	@Override
	public void invalidateAll() {
		cache.evict();
	}

	@Override
	public void put(Message message) {
		message.setText(message.getText() + " FROM CACHE");
		cache.put(message.getId(), message);
	}

	@Override
	public boolean isEmpty() {
		return cache.isEmpty();
	}

	@Override
	public void putAll(Collection<Message> list) {
		for (Message message : list) {
			put(message);
		}
	}

}
