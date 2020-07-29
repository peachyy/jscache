package com.peachyy.xcache.core.springredis;

import com.peachyy.xcache.core.Cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Slf4j
public class SpringRedisCache implements Cache {

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getName() {
        return "springRedis";
    }

    @Override
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> clazz) {
        Object obj=redisTemplate.opsForValue().get(key);
        return obj!=null ? (T)obj:null;
    }

    @Override
    public void put(Object key, Object value, long ttl) {
        redisTemplate.opsForValue().set(Objects.toString(key),value);
        if(ttl>0){
            redisTemplate.expire(Objects.toString(key),ttl, TimeUnit.SECONDS);
        }
    }

    @Override
    public void evict(Object key) {
        redisTemplate.delete(Objects.toString(key));
    }
}
