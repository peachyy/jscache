package com.peachyy.jscache.dubbo.test.provider;

import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Xs.Tao
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.peachyy.jscache.dubbo.test.impl")
@PropertySource("classpath:/provider.properties")
public class ProviderConf {

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        return providerConfig;
    }
}


