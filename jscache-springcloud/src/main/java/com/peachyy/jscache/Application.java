package com.peachyy.jscache;

import com.peachyy.jscache.core.EnableCache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Xs.Tao
 */
@SpringBootApplication
@EnableCache
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
