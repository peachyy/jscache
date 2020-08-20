package com.peachyy.jscache.core.support.localmap;

import com.peachyy.jscache.core.Cache;
import com.peachyy.jscache.core.serialize.SearializerUtils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Slf4j
public class LocalMapCache implements Cache {

    private final ConcurrentMap<Object, byte[]> LOCAL_MAP=new ConcurrentHashMap<>();

    @Override
    public String getName() {
        return "localMap";
    }

    @Override
    public Object get(Object key) {
        byte[] c=LOCAL_MAP.get(Objects.toString(key));

        return c==null ||c.length==0?null:SearializerUtils.deserialize(c);
    }

    @Override
    public <T> T get(Object key, Class<T> clazz) {
        byte[] c=LOCAL_MAP.get(Objects.toString(key));
        return c==null ||c.length==0?null:(T)SearializerUtils.deserialize(c);
    }

    @Override
    public void put(Object key, Object value, long ttl) {
        if(value!=null){
            LOCAL_MAP.put(Objects.toString(key),SearializerUtils.serialize(value));
            if(ttl>0){
                log.warn(" localMapCache  not support ttl");
            }
        }
    }

    @Override
    public void evict(Object key) {
        LOCAL_MAP.remove(Objects.toString(key));
    }

}
