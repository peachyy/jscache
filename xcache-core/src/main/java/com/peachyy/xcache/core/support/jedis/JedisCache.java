package com.peachyy.xcache.core.support.jedis;

import com.peachyy.xcache.core.Cache;
import com.peachyy.xcache.core.serialize.SearializerUtils;

import java.util.Objects;
import java.util.Properties;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Xs.Tao
 */
public class JedisCache implements Cache {
    private RedisClient redisClient;

    public final static JedisPoolConfig newPoolConfig(Properties props, String prefix) {
        JedisPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(Integer.valueOf(props.getProperty(key(prefix,"maxTotal"), "-1")));
        cfg.setMaxIdle(Integer.valueOf(props.getProperty(key(prefix,"maxIdle"), "100")));
        cfg.setMaxWaitMillis(Integer.valueOf(props.getProperty(key(prefix,"maxWaitMillis"), "100")));
        cfg.setMinEvictableIdleTimeMillis(Integer.valueOf(props.getProperty(key(prefix,"minEvictableIdleTimeMillis"), "864000000")));
        cfg.setMinIdle(Integer.valueOf(props.getProperty(key(prefix,"minIdle"), "10")));
        cfg.setNumTestsPerEvictionRun(Integer.valueOf(props.getProperty(key(prefix,"numTestsPerEvictionRun"), "10")));
        cfg.setLifo(Boolean.valueOf(props.getProperty(key(prefix,"lifo"), "false")));
        cfg.setSoftMinEvictableIdleTimeMillis(Integer.valueOf((String)props.getOrDefault(key(prefix,"softMinEvictableIdleTimeMillis"), "10")));
        cfg.setTestOnBorrow(Boolean.valueOf(props.getProperty(key(prefix,"testOnBorrow"), "true")));
        cfg.setTestOnReturn(Boolean.valueOf(props.getProperty(key(prefix,"testOnReturn"), "false")));
        cfg.setTestWhileIdle(Boolean.valueOf(props.getProperty(key(prefix,"testWhileIdle"), "true")));
        cfg.setTimeBetweenEvictionRunsMillis(Integer.valueOf(props.getProperty(key(prefix,"timeBetweenEvictionRunsMillis"), "300000")));
        cfg.setBlockWhenExhausted(Boolean.valueOf(props.getProperty(key(prefix,"blockWhenExhausted"), "false")));
        return cfg;
    }
    private static String key(String prefix, String key) {
        return (prefix == null) ? key : prefix + "." + key;
    }
    public JedisCache(Properties props,String prefix){
        JedisPoolConfig poolConfig=newPoolConfig(props,prefix);
        String hosts = props.getProperty("hosts", "47.106.117.45:6379");
        String mode = props.getProperty("mode", "single");
        String clusterName = props.getProperty("cluster_name");
        String password = props.getProperty("password","hyzh@2018");
        int database = Integer.parseInt(props.getProperty("database", "0"));
        boolean ssl = Boolean.valueOf(props.getProperty("ssl", "false"));
        this.redisClient = new RedisClient.Builder()
                .mode(mode)
                .hosts(hosts)
                .password(password)
                .cluster(clusterName)
                .database(database)
                .poolConfig(poolConfig)
                .ssl(ssl)
                .newClient();
    }
    @Override
    public String getName() {
        return "jedis";
    }

    @Override
    public Object get(Object key) {
        byte[] o=redisClient.get().get(keytoBytes(key));
        return o!=null && o.length>0?SearializerUtils.deserialize(o):null;
    }

    @Override
    public <T> T get(Object key, Class<T> clazz) {
        Object o=get(key);
        return (T)o;
    }

    @Override
    public void put(Object key, Object value, long ttl) {
        byte[] keys=keytoBytes(key);
        redisClient.get().set(keys, SearializerUtils.serialize(value));
        if(ttl>0){
            redisClient.get().expire(keys,Integer.valueOf(ttl+""));
        }

    }

    @Override
    public void evict(Object key) {
        redisClient.get().del(keytoBytes(key));
    }
    private byte[] keytoBytes(Object key){
        byte[] keys=Objects.toString(key).getBytes();
        return keys;
    }
}
