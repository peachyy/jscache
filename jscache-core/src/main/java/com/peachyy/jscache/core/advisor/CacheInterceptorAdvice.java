package com.peachyy.jscache.core.advisor;

import com.peachyy.jscache.common.CacheMetadata;
import com.peachyy.jscache.core.Cache;
import com.peachyy.jscache.core.CacheManager;
import com.peachyy.jscache.core.CacheService;
import com.peachyy.jscache.core.DefaultCacheServiceImpl;
import com.peachyy.jscache.core.DefaultKeyGenerator;
import com.peachyy.jscache.core.exception.CacheException;
import com.peachyy.jscache.core.key.KeyGenerator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.lang.reflect.Method;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Slf4j
public class CacheInterceptorAdvice  extends  CacheAnnotationSupport implements MethodInterceptor, BeanFactoryAware {
    //@Autowired
    private CacheService cacheService=null;
    private KeyGenerator keyGenerator =null;

    private CacheOperationParse cacheOperationParse;

    public CacheInterceptorAdvice() {
        cacheService=new DefaultCacheServiceImpl();
        keyGenerator=new DefaultKeyGenerator();
        setKeyGenerator(keyGenerator);
        setCacheService(cacheService);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        CacheInvoker c=()->{
            try {
                return invocation.proceed();
            } catch (Throwable throwable) {
               throw new CacheException(throwable);
            }
        };
        return execute(c,invocation.getThis().getClass(),invocation.getMethod(),invocation.getArguments());

    }

    @Override
    public CacheService getCache() {
        return this.cacheService;
    }

    @Override
    public KeyGenerator getKeyGenerator() {
        return this.keyGenerator;
    }

    @Override
    public List<CacheMetadata> getCacheMetadata(Method method, Class clazz) {
        return cacheOperationParse.findOperation(method,clazz);
    }



    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;

    }

    public void setCacheOperationParse(CacheOperationParse cacheOperationParse) {
        this.cacheOperationParse = cacheOperationParse;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if(CacheManager.getCacheManager().getCache()==null){
            try{
                CacheManager.getCacheManager().setCache(beanFactory.getBean(Cache.class));
            }catch (NoSuchBeanDefinitionException e){
                throw new CacheException(Cache.class.getName()+" not found! ");
            }

            if(CacheManager.getCacheManager().getCache()==null){
                throw new CacheException(Cache.class.getName()+" not found!! ");
            }

        }
    }
}
