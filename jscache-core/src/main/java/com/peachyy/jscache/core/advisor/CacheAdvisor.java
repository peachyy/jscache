package com.peachyy.jscache.core.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.core.Ordered;

import java.io.Serializable;

/**
 * @author Xs.Tao
 */
public  class CacheAdvisor extends AbstractPointcutAdvisor implements Serializable, Ordered {



    protected Pointcut cacheAnnationPointcut;
    protected Advice cacheInterceptorAdvice;
    private  int order;

    public CacheAdvisor(){

    }
    public CacheAdvisor(Pointcut cacheAnnationPointcut,Advice cacheInterceptorAdvice){
        this.cacheAnnationPointcut=cacheAnnationPointcut;
        this.cacheInterceptorAdvice=cacheInterceptorAdvice;
    }


    @Override
    public Pointcut getPointcut() {
        return cacheAnnationPointcut;
    }

    @Override
    public Advice getAdvice() {
        return cacheInterceptorAdvice;
    }


    public void setCacheAnnationPointcut(Pointcut cacheAnnationPointcut) {
        this.cacheAnnationPointcut = cacheAnnationPointcut;
    }

    public void setCacheInterceptorAdvice(Advice cacheInterceptorAdvice) {
        this.cacheInterceptorAdvice = cacheInterceptorAdvice;
    }


    public void setOrder(int order) {
        this.order=order;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
