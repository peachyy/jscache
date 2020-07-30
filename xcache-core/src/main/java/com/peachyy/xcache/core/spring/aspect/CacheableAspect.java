package com.peachyy.xcache.core.spring.aspect;

import com.peachyy.xcache.annation.Cacheable;
import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.core.CacheMetadataSpring;
import com.peachyy.xcache.core.CacheService;
import com.peachyy.xcache.core.DefaultKeyGenerator;
import com.peachyy.xcache.core.key.KeyGenerator;
import com.peachyy.xcache.core.monitor.MetricsMonitor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Xs.Tao
 */
@Aspect()
@Slf4j
public class CacheableAspect {

    @Autowired
    private CacheService cacheService;
    private KeyGenerator keyGenerator =new DefaultKeyGenerator();
    @Around(value = "@annotation(cacheable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, Cacheable cacheable) throws Throwable {
        CacheMetadata cacheMetadata= CacheMetadataSpring.build(proceedingJoinPoint);
        cacheMetadata.setKey(cacheable.key());
        cacheMetadata.setPrefix(cacheable.prefix());
        cacheMetadata.setCondition(cacheable.condition());
        cacheMetadata.setTtl(cacheable.ttl());
        String key=keyGenerator.generate(cacheMetadata);
        Object o =cacheService.getCache(key);
        if(o!=null){
            log.debug(" method {} cache key {} hit",cacheMetadata.getMethod(),key);
            MetricsMonitor.getCountTotal().incrementAndGet();
            return o;
        }
        Object result=proceedingJoinPoint.proceed();
        if(result!=null){
            cacheService.put(key,result,cacheable.ttl());
            log.debug("cache  key {} put success",key);
        }
        return result;
    }
}
