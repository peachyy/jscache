package com.peachyy.jscache.dubbo.test.impl;

import com.peachyy.jscache.dubbo.test.User;
import com.peachyy.jscache.dubbo.test.UserRpcService;

import org.apache.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Service
@Slf4j
public class UserRpcServiceImpl  implements UserRpcService {
    @Override
    public User findById(Integer userId) {
        log.info("获取用户ID {}",userId);
        return new User(userId,"dname"+userId);
    }

    @Override
    public void delete(Integer userId) {
            //SKIP
        log.info("删除用户ID {}",userId);
    }
}
