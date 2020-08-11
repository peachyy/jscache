package com.peachyy.xcache.core.advisor;

import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.core.CacheService;
import com.peachyy.xcache.core.DefaultCacheServiceImpl;
import com.peachyy.xcache.core.DefaultKeyGenerator;
import com.peachyy.xcache.core.exception.CacheException;
import com.peachyy.xcache.core.key.KeyGenerator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Slf4j
public class CacheInterceptorAdvice  extends  CacheAnnotationSupport implements MethodInterceptor{
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
        return execute(c,invocation.getThis(),invocation.getMethod(),invocation.getArguments());
//        CacheMetadata cacheMetadata =build(invocation);
//        cacheMetadata.setKey(cacheable.key());
//        cacheMetadata.setPrefix(cacheable.prefix());
//        cacheMetadata.setCondition(cacheable.condition());
//        cacheMetadata.setTtl(cacheable.ttl());
//        String key=keyGenerator.generate(cacheMetadata);
//        Object o =cacheService.getCache(key);
//        if(o!=null){
//            log.debug(" method {} cache key {} hit",cacheMetadata.getMethod(),key);
//            MetricsMonitor.getCountTotal().incrementAndGet();
//            return o;
//        }
//        Object result = invocation.proceed();
//        if(result!=null){
//            cacheService.put(key,result,cacheable.ttl());
//            log.debug("cache  key {} put success",key);
//        }
//        return result;
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


}
