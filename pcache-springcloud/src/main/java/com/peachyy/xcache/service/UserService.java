package com.peachyy.xcache.service;

import com.peachyy.xcache.annation.CachePut;
import com.peachyy.xcache.annation.Cacheable;
import com.peachyy.xcache.entity.User;

import java.util.List;

/**
 * @author Xs.Tao
 */
public interface UserService {

    String getName(Integer userId);

    @Cacheable(prefix = "name",key = "#p0")
    User getUserById(Integer userId);

    List<User> getUsers(Integer cache);

    void deleteUser(Integer userId);
    @CachePut(prefix = "name",key = "#p0")
    @CachePut(prefix = "aname",key = "#p0")
    User updateUserById(Integer userId);
}
