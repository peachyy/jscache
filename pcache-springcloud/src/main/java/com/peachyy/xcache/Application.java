package com.peachyy.xcache;

import com.peachyy.xcache.core.EnableCache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Xs.Tao
 */
@SpringBootApplication
@EnableCache
//@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}