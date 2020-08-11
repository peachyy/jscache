package com.peachyy.xcache.core.spring.aspect;

import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.core.exception.CacheException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Xs.Tao
 */
public class AspectSupport {
    public CacheMetadata build(CacheMetadata cacheMetadata,JoinPoint joinPoint){
        Signature signature =joinPoint.getSignature();
        if(!(signature instanceof MethodSignature)){
            throw new CacheException("fail");
        }
        MethodSignature methodSignature= (MethodSignature) signature;
        cacheMetadata.setArguments(joinPoint.getArgs());
        cacheMetadata.setParameterTypes(methodSignature.getMethod().getParameterTypes());
        cacheMetadata.setMethod(methodSignature.getMethod());
        cacheMetadata.setClazz(methodSignature.getClass());
        return cacheMetadata;
    }
}
