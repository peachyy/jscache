package com.peachyy.xcache.service.impl;

import com.peachyy.xcache.annation.Cacheable;
import com.peachyy.xcache.entity.User;
import com.peachyy.xcache.service.UserService;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getName(Integer userId) {
        log.info("GET getName");
        return "xxx";
    }

    @Override
    //@Cacheable(prefix = "name",key = "#p0")
    public User getUserById(Integer userId) {
        User user=new User();
        user.setId(userId);
        user.setName("xxx");
        log.info("GET getUserById");
        return user;
    }
}
