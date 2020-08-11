package com.peachyy.xcache.core.advisor;

import com.peachyy.xcache.common.CacheMetadata;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Xs.Tao
 */
public class CacheAnnotationPointcut extends StaticMethodMatcherPointcut implements Serializable  {

    protected Map<Method,Set<? extends Annotation>> HOLD=new HashMap<>();

    private CacheOperationParse cacheOperationParse;
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if(cacheOperationParse!=null){
            List<CacheMetadata> opertions= cacheOperationParse.findOperation(method,targetClass);
            return opertions != null && opertions.size() > 0;
        }
        return false;
    }

    public void setCacheOperationParse(CacheOperationParse cacheOperationParse) {
        this.cacheOperationParse = cacheOperationParse;
    }
}
