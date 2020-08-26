package com.peachyy.jscache.core.advisor;

import com.peachyy.jscache.core.EnableCache;
import com.peachyy.jscache.core.serialize.SearializerUtils;
import com.peachyy.jscache.core.utils.MapUtils;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
        Object mode = attributes.get("mode");
        Boolean proxyTargetClass = MapUtils.getBoolean(attributes,"proxyTargetClass");
        if(mode == AdviceMode.PROXY) {
            AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
            if(proxyTargetClass!=null && proxyTargetClass.equals(Boolean.TRUE)){
                AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
            }
        }
        Set<String>        basePackages = new LinkedHashSet<>();
        Arrays.stream(MapUtils.getStringArray(attributes,"value")).filter(StringUtils::hasText).forEach(basePackages::add);
        Arrays.stream(MapUtils.getStringArray(attributes,"basePackages")).filter(StringUtils::hasText).forEach(basePackages::add);
        RootBeanDefinition cacheAdvisor =new RootBeanDefinition(CacheAdvisor.class);
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
        cacheOperationParse.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        MutablePropertyValues parseValues=new MutablePropertyValues();
        parseValues.addPropertyValue("basePackages",basePackages.toArray(new String[]{}));
        cacheOperationParse.setPropertyValues(parseValues);
        registry.registerBeanDefinition("cacheAdvisor",cacheAdvisor);
        registry.registerBeanDefinition("cacheAnnationPointcut",cacheAnnationPointcut);
        registry.registerBeanDefinition("cacheInterceptorAdvice",cacheInterceptorAdvice);
        registry.registerBeanDefinition("cacheOperationParse",cacheOperationParse);
        SearializerUtils.init(serializer);
    }

}
