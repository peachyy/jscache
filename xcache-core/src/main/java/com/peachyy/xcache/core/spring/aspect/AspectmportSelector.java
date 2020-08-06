package com.peachyy.xcache.core.spring.aspect;

import com.peachyy.xcache.core.DefaultCacheServiceImpl;
import com.peachyy.xcache.core.serialize.SearializerUtils;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Xs.Tao
 */
public class AspectmportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        SearializerUtils.init("fastJson");
        return new String[]{CacheableAspect.class.getName(),CacheEvictAspect.class.getName(), DefaultCacheServiceImpl.class.getName()};
    }
}
