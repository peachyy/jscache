package com.peachyy.jscache.dubbo.test.consumer;

import com.peachyy.jscache.core.support.localmap.LocalMapCache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xs.Tao
 */
@Configuration
public class CacheConfig {

    @Bean
    public LocalMapCache cache(){
        return new LocalMapCache();
    }
}
