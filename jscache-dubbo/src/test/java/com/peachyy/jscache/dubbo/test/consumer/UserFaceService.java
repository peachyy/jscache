package com.peachyy.jscache.dubbo.test.consumer;

import com.peachyy.jscache.dubbo.test.User;
import com.peachyy.jscache.dubbo.test.UserRpcService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @author Xs.Tao
 */
@Service
public class UserFaceService {


    @Reference
    private UserRpcService userRpcService;

    public User byId(Integer userId){
        return userRpcService.findById(userId);
    }

    public void delete(Integer userId){
        userRpcService.delete(userId);
    }
}
