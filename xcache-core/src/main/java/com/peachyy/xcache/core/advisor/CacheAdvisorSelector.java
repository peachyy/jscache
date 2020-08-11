package com.peachyy.xcache.core.advisor;

import com.peachyy.xcache.core.serialize.SearializerUtils;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Xs.Tao
 */
public class CacheAdvisorSelector implements ImportBeanDefinitionRegistrar {



    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition cacheAdvisor=new RootBeanDefinition(CacheAdvisor.class);
        cacheAdvisor.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        cacheAdvisor.setAutowireMode( AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);

        RootBeanDefinition cacheAnnationPointcut=new RootBeanDefinition(CacheAnnotationPointcut.class);
        cacheAnnationPointcut.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        cacheAnnationPointcut.setAutowireMode( AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);

        RootBeanDefinition cacheInterceptorAdvice=new RootBeanDefinition(CacheInterceptorAdvice.class);
        cacheInterceptorAdvice.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        cacheInterceptorAdvice.setAutowireMode( AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);


        RootBeanDefinition cacheOperationParse=new RootBeanDefinition(CacheAnnoationOperationParse.class);
        cacheInterceptorAdvice.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        registry.registerBeanDefinition("cacheAdvisor",cacheAdvisor);
        registry.registerBeanDefinition("cacheAnnationPointcut",cacheAnnationPointcut);
        registry.registerBeanDefinition("cacheInterceptorAdvice",cacheInterceptorAdvice);
        registry.registerBeanDefinition("cacheOperationParse",cacheOperationParse);
        SearializerUtils.init("fastJson");
    }
}
