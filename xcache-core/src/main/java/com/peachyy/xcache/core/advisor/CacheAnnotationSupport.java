package com.peachyy.xcache.core.advisor;

import com.peachyy.xcache.common.CacheEvictMetadata;
import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.common.CachePutMetadata;
import com.peachyy.xcache.common.CacheableMetadata;
import com.peachyy.xcache.core.CacheService;
import com.peachyy.xcache.core.exception.CacheException;
import com.peachyy.xcache.core.key.KeyGenerator;

import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
                    .map(it->(CacheableMetadata)it).collect(Collectors.toList());
            for(CacheableMetadata it:cacheableMetadata){
                String key=generate(it,context.getArgs());
                Object result=getCache(key);
                if(result!=null){
                    log.debug("{} key {} hit",it.getMethod().getName(),key);
                    return result;
                }
            }
        }
        return null;
    }
    protected void doEvcits(OperatorContext context){
        if(!CollectionUtils.isEmpty(context.getMetadata())){
            List<CacheEvictMetadata> evictMetadata=context.getMetadata().stream()
                    .map(it->(CacheEvictMetadata)it).collect(Collectors.toList());
            evictMetadata.forEach(it->{
                String key=generate(it,context.getArgs());
                getCache().delete(key);
                log.debug(" {} key {} evcit!",it.getMethod().getName(),key);
            });
        }
    }
    protected void doPuts(OperatorContext context,Object resultVal){
        if(!CollectionUtils.isEmpty(context.getMetadata())){
            List<CachePutMetadata> putMetadata=context.getMetadata().stream()
                    .map(it->(CachePutMetadata)it).collect(Collectors.toList());
            putMetadata.forEach(it->{
                String key=generate(it,context.getArgs());
                if(it.getTtl()>0){
                    getCache().put(key,resultVal,it.getTtl());
                    log.debug(" {} key {} ttl {} put!",it.getMethod().getName(),key,it.getTtl());
                }else{
                    getCache().put(key,resultVal);
                    log.debug(" {} key {}  put!",it.getMethod().getName(),key);
                }
            });
        }
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
                put.setCondition(it.getCondition());
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
