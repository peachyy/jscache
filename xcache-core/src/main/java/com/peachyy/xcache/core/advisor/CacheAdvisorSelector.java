package com.peachyy.xcache.core.advisor;

import com.peachyy.xcache.core.EnableCache;
import com.peachyy.xcache.core.serialize.SearializerUtils;
import com.peachyy.xcache.core.utils.MapUtils;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * @author Xs.Tao
 */
public class CacheAdvisorSelector implements ImportBeanDefinitionRegistrar {



    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object>           attributes   = importingClassMetadata.getAnnotationAttributes(EnableCache.class.getName(),
                false);
        int order= MapUtils.getInteger(attributes,"order");
        String serializer=MapUtils.getString(attributes,"serializer","fastJson");
        RootBeanDefinition            cacheAdvisor =new RootBeanDefinition(CacheAdvisor.class);
        cacheAdvisor.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        cacheAdvisor.setAutowireMode( AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);
        MutablePropertyValues values=new MutablePropertyValues();
        values.addPropertyValue("order",order);
        cacheAdvisor.setPropertyValues(values);
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
        SearializerUtils.init(serializer);
    }

}
