package it.jesty.gaestbook.cache;

import javax.cache.CacheException;

public interface GaestbookCacheFactory {

	GaestbookCacheWrapper getInstance() throws CacheException;

}
