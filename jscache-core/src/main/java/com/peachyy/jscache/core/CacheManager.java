package com.peachyy.jscache.core;

/**
 * @author Xs.Tao
 */
public class CacheManager {

    private static CacheManager cacheManager=new CacheManager();

    private Cache cache;

    private CacheManager(){

    }
    public static CacheManager getCacheManager(){
        return cacheManager;
    }
    public Cache getCache(){
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
