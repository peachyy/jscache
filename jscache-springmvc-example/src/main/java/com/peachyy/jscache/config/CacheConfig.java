package com.peachyy.jscache.config;

import com.peachyy.jscache.core.support.jedis.JedisCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Xs.Tao
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class CacheConfig {
    @Autowired
    private RedisProperties redisProperties;
    @Bean
    public JedisCache cache(){
        Properties properties=new Properties();
        properties.put("hosts",redisProperties.getHost().concat(":")+redisProperties.getPort());
        properties.put("password",redisProperties.getPassword());
        properties.put("database",redisProperties.getDatabase());
        return new JedisCache(properties,null);
    }
}
