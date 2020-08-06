package com.peachyy.xcache.service;

import com.peachyy.xcache.entity.User;

import java.util.List;

/**
 * @author Xs.Tao
 */
public interface UserService {

    String getName(Integer userId);


    User getUserById(Integer userId);

    List<User> getUsers(Integer cache);
}
