package com.peachyy.jscache.dubbo.test.consumer;

import com.peachyy.jscache.dubbo.test.User;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConsumerBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        final UserFaceService userFaceService =  context.getBean(UserFaceService.class);
        User user=userFaceService.byId(1);
        System.out.println(user);
        userFaceService.byId(1);
        userFaceService.byId(1);
        userFaceService.delete(1);

        System.out.println("dubbo consumer success.");
    }


}