package com.peachyy.jscache.dubbo.test;

import com.peachyy.jscache.annation.CacheEvict;
import com.peachyy.jscache.annation.Cacheable;

/**
 * @author Xs.Tao
 */
public interface UserRpcService {

    @Cacheable(prefix = "user",key = "#p0",returnCondition = "#result!=null")
    User findById(Integer userId);

    @CacheEvict(prefix = "user",key = "#p0")
    void delete(Integer userId);

}
