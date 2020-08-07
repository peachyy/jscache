package com.peachyy.xcache.core.advisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Slf4j
public class CacheInterceptorAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object ret = invocation.proceed();
        return ret;
    }
}
