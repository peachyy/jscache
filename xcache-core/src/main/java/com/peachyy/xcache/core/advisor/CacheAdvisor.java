package com.peachyy.xcache.core.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

import java.io.Serializable;

/**
 * @author Xs.Tao
 */
public  class CacheAdvisor extends AbstractPointcutAdvisor implements Serializable {



    protected Pointcut pointcut;
    protected Advice advice;

    public CacheAdvisor(){

    }
    public CacheAdvisor(Pointcut pointcut,Advice advice){
        this.pointcut=pointcut;
        this.advice=advice;
    }


    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
