package com.peachyy.jscache.controller;

import com.peachyy.jscache.entity.User;
import com.peachyy.jscache.service.OrderService;
import com.peachyy.jscache.service.UserService;

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
    @Autowired
    private OrderService orderService;

    @GetMapping("/nullValue")
    @ResponseBody
    public User nullValue(Integer userId){
        User user= userService.nullValue(userId);
        return user;
    }
    @GetMapping("/byId")
    @ResponseBody
    public User getUserById(Integer userId){
        User user= userService.getUserById(userId);
        return user;
    }
    @GetMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(Integer userId){
        userService.deleteUser(userId);
        return "ok";
    }
    @GetMapping("/updateUser")
    @ResponseBody
    public User updateUserById(Integer userId){
        User user= userService.updateUserById(userId);
        return user;
    }
    @GetMapping("/byId2")
    @ResponseBody
    public List<User> getUserById2(Integer userId){
        List<User> user= userService.getUsers(userId);
        return user;
    }
    @GetMapping("/orderBy")
    @ResponseBody
    public String orderBy(Integer userId){
        return orderService.say();
    }
}
