package com.peachyy.jscache.core.advisor;

import com.peachyy.jscache.common.CacheEvictMetadata;
import com.peachyy.jscache.common.CacheMetadata;
import com.peachyy.jscache.common.CachePutMetadata;
import com.peachyy.jscache.common.CacheableMetadata;
import com.peachyy.jscache.core.CacheService;
import com.peachyy.jscache.core.exception.CacheException;
import com.peachyy.jscache.core.key.KeyGenerator;
import com.peachyy.jscache.core.support.NullValue;

import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Slf4j
public abstract class CacheAnnotationSupport   {




    public Object execute(CacheInvoker invoker, Object this_, Method method, Object[] args){
        List<CacheMetadata> cacheMetadata=getCacheMetadata(method,this_.getClass());
        if(CollectionUtils.isEmpty(cacheMetadata)){
            return invoker.invoke();
        }
        return execute(invoker,cacheMetadata,this_,method,args);
    }

    public Object execute(CacheInvoker invoker,List<CacheMetadata> cacheMetadata, Object this_, Method method,Object[] args){
       MultiValueMap<Class<? extends CacheMetadata>,CacheMetadata> cacheMetadataMaps=
               new LinkedMultiValueMap<>(cacheMetadata.size());
        cacheMetadata.forEach(it->{
            if(it instanceof CacheableMetadata){
                CacheableMetadata metadata=(CacheableMetadata)it;
                cacheMetadataMaps.add(metadata.getClass(),metadata);
            }else  if(it instanceof CacheEvictMetadata){
                CacheEvictMetadata metadata=(CacheEvictMetadata)it;
                cacheMetadataMaps.add(metadata.getClass(),metadata);
            }else  if(it instanceof CachePutMetadata){
                CachePutMetadata metadata=(CachePutMetadata)it;
                cacheMetadataMaps.add(metadata.getClass(),metadata);
            }else{
                throw new CacheException("not support annotation "+it.getClass());
            }
        });
        //cacheable
        List<CacheMetadata> cacheables=cacheMetadataMaps.get(CacheableMetadata.class);
        //put
        List<CacheMetadata> puts=cacheMetadataMaps.get(CachePutMetadata.class);

        Object resultVal=doCacheable(new OperatorContext(cacheables,args));
        if(resultVal!=null && CollectionUtils.isEmpty(puts)){
            //没有put标识 那么就直接获取缓存
        }else{
            resultVal=invoker.invoke();
            List<CachePutMetadata> resolvPuts=resolvingCacheableMetadata(cacheables);
            if(!CollectionUtils.isEmpty(resolvPuts)){
                if(puts==null){
                    puts=new ArrayList<>();
                }
                puts.addAll(resolvPuts);
            }
        }
        //puts
        doPuts(new OperatorContext(puts,args),resultVal);
        //evict
        List<CacheMetadata> evicts=cacheMetadataMaps.get(CacheEvictMetadata.class);
        doEvcits(new OperatorContext(evicts,args));
        return resultVal;
    }
    protected String generate(CacheMetadata cacheMetadata,Object[] arguments){
        return getKeyGenerator().generate(cacheMetadata,arguments);
    }
    protected Object getCache(String key){
        return getCache().getCache(key);
    }


    protected Object doCacheable(OperatorContext context){
        if(!CollectionUtils.isEmpty(context.getMetadata())){
            List<CacheableMetadata> cacheableMetadata=context.getMetadata().stream()
                    .map(it->(CacheableMetadata)it).
                    filter(it->StringUtils.isEmpty(it.getArgCondition()) || getKeyGenerator().argCondition(it,context.getArgs())).collect(Collectors.toList());
            for(CacheableMetadata it:cacheableMetadata){
                String key=generate(it,context.getArgs());
                Object result=getCache(key);
                if(result!=null){
                    log.debug("{}->{} key {} hit",it.getClazz().getName(),it.getMethod().getName(),key);
                    return unWrapNullValue(result);
                }
            }
        }
        return null;
    }
    protected void doEvcits(OperatorContext context){
        if(!CollectionUtils.isEmpty(context.getMetadata())){
            List<CacheEvictMetadata> evictMetadata=context.getMetadata().stream()
                    .map(it->(CacheEvictMetadata)it)
                    .filter(it->StringUtils.isEmpty(it.getArgCondition()) || getKeyGenerator().argCondition(it,context.getArgs()))
                    .collect(Collectors.toList());
            evictMetadata.forEach(it->{
                String key=generate(it,context.getArgs());
                getCache().delete(key);
                log.debug("{}->{} key {} delete success!",it.getClazz().getName(),it.getMethod().getName(),key);
            });
        }
    }
    protected void doPuts(OperatorContext context,Object resultVal){
        if(!CollectionUtils.isEmpty(context.getMetadata())){
            List<CachePutMetadata> putMetadata=context.getMetadata().stream()
                    .map(it->(CachePutMetadata)it)
                    .filter(it->StringUtils.isEmpty(it.getArgCondition()) || getKeyGenerator().argCondition(it,context.getArgs()))
                    .collect(Collectors.toList());
            putMetadata.forEach(it->{
                //null value
                if(!it.isAllowNullValue() && resultVal==null){
                    return;
                }
                if(getKeyGenerator().returnCondition(it,resultVal)){
                    String key=generate(it,context.getArgs());
                    if(it.getTtl()>0){
                        getCache().put(key,wrapNullValue(resultVal,it.isAllowNullValue()),it.getTtl());
                        log.debug("{}->{} key {} ttl {} put success!",it.getClazz().getName(),it.getMethod().getName(),key,it.getTtl());
                    }else{
                        getCache().put(key,wrapNullValue(resultVal,it.isAllowNullValue()));
                        log.debug("{}->{} key {}  put success!",it.getClazz().getName(),it.getMethod().getName(),key);
                    }
                }
            });
        }
    }
    protected Object wrapNullValue(Object reuslt,boolean isAllowNullVlaue){
        if(reuslt==null && isAllowNullVlaue){
            return NullValue.INSTANCE;
        }
        return reuslt;
    }
    protected Object unWrapNullValue(Object reuslt){
        if(NullValue.INSTANCE == reuslt){
            return null;
        }
        return reuslt;
    }
    public abstract CacheService getCache();

    public abstract KeyGenerator getKeyGenerator();

    public List<CacheMetadata> getCacheMetadata(Method method,Class clazz){
        throw new RuntimeException("not implements");
    }


    private List<CachePutMetadata> resolvingCacheableMetadata(List<CacheMetadata> cacheMetadataList){
        if(!CollectionUtils.isEmpty(cacheMetadataList)) {
            List<CacheableMetadata> cacheableMetadata = cacheMetadataList.stream()
                    .map(it -> (CacheableMetadata) it).collect(Collectors.toList());
            List<CachePutMetadata> putMetadata=new ArrayList<>(cacheMetadataList.size());
            for (CacheableMetadata it : cacheableMetadata) {
                CachePutMetadata put=new CachePutMetadata();
                put.setAllowNullValue(it.isAllowNullValue());
                put.setAnnotation(null);
                put.setTtl(it.getTtl());
                put.setClazz(it.getClazz());
                put.setReturnCondition(it.getReturnCondition());
                put.setKey(it.getKey());
                put.setMethod(it.getMethod());
                put.setParameterTypes(it.getParameterTypes());
                put.setPrefix(it.getPrefix());
                putMetadata.add(put);
            }
            return putMetadata;
        }
        return null;
    }

    @AllArgsConstructor
    @Getter
    static class OperatorContext{
        private List<CacheMetadata> metadata;
        private Object[] args;
    }
}
