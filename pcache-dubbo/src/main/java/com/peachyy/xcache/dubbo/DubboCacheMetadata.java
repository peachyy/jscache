package com.peachyy.xcache.dubbo;

import com.peachyy.xcache.common.CacheMetadata;

import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author Xs.Tao
 */
public class DubboCacheMetadata extends CacheMetadata {
    public static CacheMetadata build(CacheMetadata cacheMetadata,Invoker<?> invoker, Invocation invocation){
        cacheMetadata.setArguments(invocation.getArguments());
        cacheMetadata.setParameterTypes(invocation.getParameterTypes());
        Method method = ReflectionUtils.findMethod(invoker.getInterface(),invocation.getMethodName(),invocation.getParameterTypes());
        cacheMetadata.setMethod(method);
        cacheMetadata.setClazz(invoker.getInterface());
        return cacheMetadata;
    }
}
