package com.peachyy.xcache.core.spring.aspect;

import com.peachyy.xcache.annation.CacheEvict;
import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.core.CacheMetadataSpring;
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
public class CacheEvictAspect {

    @Autowired
    private CacheService cacheService;

    private KeyGenerator keyGenerator=new DefaultKeyGenerator();

    @After(value = "@annotation(cacheEvict)")
    public Object around(JoinPoint joinPoint, CacheEvict cacheEvict) throws Throwable {
        //Signature signature =joinPoint.getSignature();
        CacheMetadata cacheMetadata=CacheMetadataSpring.build(joinPoint);
        cacheMetadata.setKey(cacheEvict.key());
        cacheMetadata.setPrefix(cacheEvict.prefix());
        cacheMetadata.setCondition(cacheEvict.condition());
        String key=keyGenerator.generate(cacheMetadata);
        cacheService.delete(key);
        log.debug("delete cache key {} method %s#(%s)",key,
                cacheMetadata.getMethod().getName(),cacheMetadata.getArguments());
        return null;
    }
}
