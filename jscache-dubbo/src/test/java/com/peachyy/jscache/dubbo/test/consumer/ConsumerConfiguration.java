package com.peachyy.jscache.dubbo.test.consumer;

import com.peachyy.jscache.core.EnableCache;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableCache
@Configuration
@EnableDubbo(scanBasePackages = "com.peachyy.jscache.dubbo.test.consumer")
@PropertySource("classpath:/consumer.properties")
@ComponentScan(value = {"com.peachyy.jscache.dubbo.test.consumer"})
public class ConsumerConfiguration {

}