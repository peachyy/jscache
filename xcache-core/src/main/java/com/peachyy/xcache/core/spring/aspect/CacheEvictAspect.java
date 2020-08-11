package com.peachyy.xcache.core.spring.aspect;

import com.peachyy.xcache.annation.CacheEvict;
import com.peachyy.xcache.common.CacheEvictMetadata;
import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.core.CacheService;
import com.peachyy.xcache.core.DefaultKeyGenerator;
import com.peachyy.xcache.core.key.KeyGenerator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Xs.Tao
 */
@Aspect()
@Slf4j
public class CacheEvictAspect extends AspectSupport{

    @Autowired
    private CacheService cacheService;

    private KeyGenerator keyGenerator=new DefaultKeyGenerator();

    @After(value = "@annotation(cacheEvict)")
    public Object around(JoinPoint joinPoint, CacheEvict cacheEvict) throws Throwable {
        //Signature signature =joinPoint.getSignature();
        CacheEvictMetadata cacheEvictMetadata =new CacheEvictMetadata();
        build(cacheEvictMetadata,joinPoint);
        cacheEvictMetadata.setKey(cacheEvict.key());
        cacheEvictMetadata.setPrefix(cacheEvict.prefix());
        cacheEvictMetadata.setCondition(cacheEvict.condition());
        String key=keyGenerator.generate(cacheEvictMetadata);
        cacheService.delete(key);
        log.debug("delete cache key {} method %s#(%s)",key,
                cacheEvictMetadata.getMethod().getName(),cacheEvictMetadata.getArguments());
        return null;
    }
}
