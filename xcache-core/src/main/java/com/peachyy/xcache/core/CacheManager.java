package com.peachyy.xcache.core;

import com.peachyy.xcache.core.jedis.JedisCache;
import com.peachyy.xcache.core.springredis.SpringRedisCache;

import java.util.Properties;

/**
 * @author Xs.Tao
 */
public class CacheManager {

    private static CacheManager cacheManager=new CacheManager();

    private CacheManager(){

    }
    public static CacheManager getCacheManager(){
        return cacheManager;
    }
    public Cache getCache(){
        return new JedisCache(new Properties(),null);
    }
}
