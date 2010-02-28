package it.jesty.gaestbook.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.cache.Cache;
import javax.cache.CacheEntry;
import javax.cache.CacheListener;
import javax.cache.CacheStatistics;

public class GaestbookCacheWrapper  {
	
	public void addListener(CacheListener arg0) {
		cache.addListener(arg0);
	}

	public void clear() {
		cache.clear();
	}

	public boolean containsKey(Object arg0) {
		return cache.containsKey(arg0);
	}

	public boolean containsValue(Object arg0) {
		return cache.containsValue(arg0);
	}

	public Set entrySet() {
		return cache.entrySet();
	}

	public void evict() {
		cache.evict();
	}

	public Object get(Object arg0) {
		return cache.get(arg0);
	}

	public Map getAll(Collection arg0) {
		return cache.getAll(arg0);
	}

	public CacheEntry getCacheEntry(Object arg0) {
		return cache.getCacheEntry(arg0);
	}

	public CacheStatistics getCacheStatistics() {
		return cache.getCacheStatistics();
	}

	public boolean isEmpty() {
		return cache.isEmpty();
	}

	public Set keySet() {
		return cache.keySet();
	}

	public void load(Object arg0) {
		cache.load(arg0);
	}

	public void loadAll(Collection arg0) {
		cache.loadAll(arg0);
	}

	public Object peek(Object arg0) {
		return cache.peek(arg0);
	}

	public Object put(Object arg0, Object arg1) {
		return cache.put(arg0, arg1);
	}

	public void putAll(Map arg0) {
		cache.putAll(arg0);
	}

	public Object remove(Object arg0) {
		return cache.remove(arg0);
	}

	public void removeListener(CacheListener arg0) {
		cache.removeListener(arg0);
	}

	public int size() {
		return cache.size();
	}

	public Collection values() {
		return cache.values();
	}

	private final Cache cache;

	public GaestbookCacheWrapper(Cache cache) {
		this.cache = cache;
	}
	
	public Cache getCache() {
		return cache;
	}
	
}
