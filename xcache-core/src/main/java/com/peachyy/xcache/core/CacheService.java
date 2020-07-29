package com.peachyy.xcache.core;

/**
 * @author Xs.Tao
 */
public interface CacheService {

    Object getCache(Object key);

    void put(Object key,Object value);

    void put(Object key,Object value,long ttl);

    void delete(Object key);


}
