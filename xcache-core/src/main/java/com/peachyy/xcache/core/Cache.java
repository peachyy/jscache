package com.peachyy.xcache.core;

/**
 * @author Xs.Tao
 */
public interface Cache {

    String getName();

    Object get(Object key);

    <T> T get(Object key, Class<T> clazz);

    default void put(Object key,Object value){
        put(key,value,0);
    }
    void put(Object key,Object value,long ttl);
    void evict(Object key);

    default boolean exsis(Object key){
        return null!=get(key);
    }

    interface ValueWrapper {
        /**
         * Return the actual value in the cache.
         */
        Object get();
    }
}
