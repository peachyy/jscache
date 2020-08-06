package com.peachyy.xcache.controller;

import com.peachyy.xcache.entity.User;
import com.peachyy.xcache.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Xs.Tao
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/byId")
    @ResponseBody
    public User getUserById(Integer userId){
        User user= userService.getUserById(userId);
        return user;
    }
    @GetMapping("/byId2")
    @ResponseBody
    public List<User> getUserById2(Integer userId){
        List<User> user= userService.getUsers(userId);
        return user;
    }
}
