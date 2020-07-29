package com.peachyy.xcache.service;

import com.peachyy.xcache.entity.User;

/**
 * @author Xs.Tao
 */
public interface UserService {

    String getName(Integer userId);


    User getUserById(Integer userId);
}
