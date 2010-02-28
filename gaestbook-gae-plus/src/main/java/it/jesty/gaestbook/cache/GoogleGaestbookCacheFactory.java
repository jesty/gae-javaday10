package it.jesty.gaestbook.cache;

import java.util.HashMap;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import org.springframework.stereotype.Component;

@Component
public class GoogleGaestbookCacheFactory implements GaestbookCacheFactory {
	
	public GaestbookCacheWrapper getInstance() throws CacheException{
		 CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
		 Cache cache = cacheFactory.createCache(new HashMap());
         return new GaestbookCacheWrapper(cache);
	}

}
