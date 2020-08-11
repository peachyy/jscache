package com.peachyy.jscache.dubbo;

import com.peachyy.jscache.annation.Cacheable;
import com.peachyy.jscache.common.CacheableMetadata;
import com.peachyy.jscache.core.CacheService;
import com.peachyy.jscache.core.DefaultKeyGenerator;
import com.peachyy.jscache.core.key.KeyGenerator;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Activate(group = CommonConstants.CONSUMER, order = Integer.MIN_VALUE + 1)
@Slf4j
public class CacheFilter implements Filter {

    private KeyGenerator keyGenerator=new DefaultKeyGenerator();

    private CacheService cacheService;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        CacheableMetadata cacheMetadata=new CacheableMetadata();
        DubboCacheMetadata.build(cacheMetadata,invoker,invocation);
        if(cacheMetadata.getMethod().isAnnotationPresent(Cacheable.class)){
            Cacheable cacheable =cacheMetadata.getMethod().getAnnotation(Cacheable.class);
            cacheMetadata.setKey(cacheable.key());
            cacheMetadata.setPrefix(cacheable.prefix());
            cacheMetadata.setCondition(cacheable.condition());
            cacheMetadata.setTtl(cacheable.ttl());
            String key=keyGenerator.generate(cacheMetadata);
            Object o=cacheService.getCache(key);
            if(o!=null){
                return  AsyncRpcResult.newDefaultAsyncResult(o, invocation);
            }
        }


        //设置缓存？
        return invoker.invoke(invocation);
    }
}
