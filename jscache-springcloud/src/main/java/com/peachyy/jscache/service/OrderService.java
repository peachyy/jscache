package com.peachyy.jscache.service;

import com.peachyy.jscache.annation.CacheEvict;

/**
 * @author Xs.Tao
 */
public interface OrderService {

    @CacheEvict(key = "'aa'",prefix = "xxx")
    public String say();
}
