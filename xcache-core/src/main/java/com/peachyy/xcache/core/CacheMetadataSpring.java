package com.peachyy.xcache.core;

import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.core.exception.CacheException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Xs.Tao
 */
public class CacheMetadataSpring extends CacheMetadata {

    public static CacheMetadata build(JoinPoint joinPoint){
        Signature signature=joinPoint.getSignature();
        if(!(signature instanceof MethodSignature)){
           throw new CacheException("fail");
        }
        MethodSignature methodSignature= (MethodSignature) signature;
        CacheMetadata cacheMetadata=new CacheMetadata();
        cacheMetadata.setArguments(joinPoint.getArgs());
        cacheMetadata.setParameterTypes(methodSignature.getMethod().getParameterTypes());
        cacheMetadata.setMethod(methodSignature.getMethod());
        cacheMetadata.setClazz(methodSignature.getClass());
        return cacheMetadata;
    }
}
