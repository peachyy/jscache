package com.peachyy.jscache.config;

import com.peachyy.jscache.core.support.jedis.JedisCache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Xs.Tao
 */
@Configuration
public class CacheConfig {
    @Bean
    public JedisCache cache(){
        Properties properties=new Properties();
        properties.put("hosts","47.106.117.45:6379");
        properties.put("password","hyzh@2018");
        return new JedisCache(properties,null);
    }
}
