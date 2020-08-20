package com.peachyy.jscache.dubbo;

import com.peachyy.jscache.common.CacheMetadata;
import com.peachyy.jscache.core.CacheService;
import com.peachyy.jscache.core.DefaultCacheServiceImpl;
import com.peachyy.jscache.core.DefaultKeyGenerator;
import com.peachyy.jscache.core.advisor.CacheAnnoationOperationParse;
import com.peachyy.jscache.core.advisor.CacheAnnotationSupport;
import com.peachyy.jscache.core.advisor.CacheOperationParse;
import com.peachyy.jscache.core.key.KeyGenerator;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Activate(group = CommonConstants.CONSUMER, order = Integer.MIN_VALUE + 1)
@Slf4j
public class CacheFilter extends CacheAnnotationSupport implements Filter {

    private KeyGenerator keyGenerator=new DefaultKeyGenerator();

    private CacheService cacheService=new DefaultCacheServiceImpl();

    private CacheOperationParse cacheOperationParse=new CacheAnnoationOperationParse();
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Method              method        = ReflectionUtils.findMethod(invoker.getInterface(),
                invocation.getMethodName(),invocation.getParameterTypes());
        List<CacheMetadata> lst           = cacheOperationParse.findOperation(method,invoker.getInterface());
        if(CollectionUtils.isEmpty(lst)){
            return invoker.invoke(invocation);
        }
        Object reuslt= execute(()->{
                    Result result=invoker.invoke(invocation);
                    if(result!=null && !result.hasException()){
                        return result.getValue();
                    }
                    return result;
                },
                invoker.getInterface(),method,invocation.getArguments());
        return  AsyncRpcResult.newDefaultAsyncResult(reuslt, invocation);
    }

    @Override
    public CacheService getCache() {
        return cacheService;
    }

    @Override
    public List<CacheMetadata> getCacheMetadata(Method method, Class clazz) {
        List<CacheMetadata> lst           = cacheOperationParse.findOperation(method,clazz);
        return lst;
    }

    @Override
    public KeyGenerator getKeyGenerator() {
        return keyGenerator;
    }
}
