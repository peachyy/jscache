package com.peachyy.xcache;

import com.peachyy.xcache.core.spring.aspect.EnableCache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Xs.Tao
 */
@SpringBootApplication
@EnableCache
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
