package com.peachyy.xcache.service.impl;

import com.peachyy.xcache.annation.CacheEvict;
import com.peachyy.xcache.annation.Cacheable;
import com.peachyy.xcache.entity.User;
import com.peachyy.xcache.service.UserService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Cacheable(prefix = "name",key = "#p0")
    public User getUserById(Integer userId) {
        User user=new User();
        user.setId(userId);
        user.setName("xxx");
        log.info("GET getUserById");
        return user;
    }

    @Override
    @Cacheable(prefix = "names",key = "#p0")
    public List<User> getUsers(Integer cache) {
        User user1=new User();
        user1.setId(cache);
        user1.setName("user1");
        User user2=new User();
        user2.setId(cache);
        user2.setName("user2");
        User user3=new User();
        user3.setId(cache);
        user3.setName("user3");
        User user4=new User();
        user4.setId(cache);
        user4.setName("user4");
        List<User> list=new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        return list;
    }

    @Override
    @CacheEvict(prefix = "name",key = "#p0")
    public void deleteUser(Integer userId) {
        log.info("删除了用户{}",userId);
    }

    @Override

    public User updateUserById(Integer userId) {
        User user=new User();
        user.setId(userId);
        user.setName("xxxupdate");
        log.info("GET updateUserById");
        return user;
    }
}
