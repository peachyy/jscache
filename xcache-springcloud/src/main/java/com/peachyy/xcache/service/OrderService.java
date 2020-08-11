package com.peachyy.xcache.service;

import com.peachyy.xcache.annation.CacheEvict;

/**
 * @author Xs.Tao
 */
public interface OrderService {

    @CacheEvict(key = "",prefix = "")
    public String say();
}
