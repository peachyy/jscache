package com.peachyy.jscache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xs.Tao
 */
@ConfigurationProperties("redis")
@Getter
@Setter
public class RedisProperties {
    private int database;
    private String host;
    private int port=6379;
    private String password;
}
