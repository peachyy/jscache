package com.peachyy.xcache.core;

/**
 * @author Xs.Tao
 */
public class DefaultCacheServiceImpl implements CacheService {
    private CacheManager cacheManager=CacheManager.getCacheManager();
    @Override
    public Object getCache(Object key) {
        return cacheManager.getCache().get(key);
    }

    @Override
    public void put(Object key, Object value) {
        cacheManager.getCache().put(key,value);
    }

    @Override
    public void put(Object key, Object value, long ttl) {
        cacheManager.getCache().put(key,value,ttl);
    }

    @Override
    public void delete(Object key) {
        cacheManager.getCache().evict(key);
    }
}
