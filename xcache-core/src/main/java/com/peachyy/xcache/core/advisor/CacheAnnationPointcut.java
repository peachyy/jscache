package com.peachyy.xcache.core.advisor;

import com.peachyy.xcache.annation.CacheEvict;
import com.peachyy.xcache.annation.Cacheable;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xs.Tao
 */
public class CacheAnnationPointcut extends StaticMethodMatcherPointcut implements Serializable  {
    protected static List<Class<? extends Annotation> > supportAnnations;
    static {
        supportAnnations=new ArrayList<>(3);
        supportAnnations.add(Cacheable.class);
        supportAnnations.add(CacheEvict.class);
    }
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if(targetClass.getName().contains("com.peachyy")){
            return true;
        }
        return false;
    }

    public boolean isSupport(Class<? extends  Annotation> target){
        return supportAnnations.stream().anyMatch(it->it.getName().equals(target.getName()));
    }

//    public boolean isSupport(Method method){
//
//        AnnotationUtils.getAnnotations()
//    }
}
