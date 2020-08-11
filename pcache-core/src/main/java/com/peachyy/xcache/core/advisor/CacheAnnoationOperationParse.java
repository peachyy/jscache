package com.peachyy.xcache.core.advisor;

import com.peachyy.xcache.annation.CacheEvict;
import com.peachyy.xcache.annation.CachePut;
import com.peachyy.xcache.annation.Cacheable;
import com.peachyy.xcache.common.CacheEvictMetadata;
import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.common.CachePutMetadata;
import com.peachyy.xcache.common.CacheableMetadata;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xs.Tao
 */
public class CacheAnnoationOperationParse implements CacheOperationParse{

    private final    Map<CacheKey, List<CacheMetadata>> cachesHold = new ConcurrentHashMap<>(1024);
    protected static Set<Class<? extends Annotation> >  supportAnnations;
    static {
        supportAnnations=new HashSet<>(5);
        supportAnnations.add(Cacheable.class);
        supportAnnations.add(CacheEvict.class);
        supportAnnations.add(CachePut.class);
    }
    private final List<CacheMetadata> empty= Collections.emptyList();


    @Override
    public List<CacheMetadata> findOperation(Method method,Class targetClass){
        CacheKey cacheKey=new CacheKey(method,targetClass);
        if(!cachesHold.containsKey(cacheKey)){
            Optional<List<CacheMetadata>> lstOptional=loadOperation(method,targetClass);
            if(lstOptional.isPresent()) {
                cachesHold.putIfAbsent(cacheKey,lstOptional.get());
            }else{
                cachesHold.putIfAbsent(cacheKey,empty);
            }
        }
        List<CacheMetadata> hits=cachesHold.get(cacheKey);
        if(hits!=null){
            return hits==empty?null:hits;
        }
        return null;
    }

    protected Optional<List<CacheMetadata>> loadOperation(final Method method, final Class targetClass){
        Set<Annotation> annotations=AnnotatedElementUtils.findAllMergedAnnotations(method,supportAnnations);
        if(annotations.isEmpty()){
            return Optional.empty();
        }
        List<CacheMetadata> ops=new ArrayList<>();
        annotations.stream().filter(it->it instanceof Cacheable).
                forEach(it-> ops.add(doPraseCacheable((Cacheable) it,method,targetClass)));
        annotations.stream().filter(it->it instanceof CacheEvict).
                forEach(it-> ops.add(doPraseEvict((CacheEvict) it,method,targetClass)));
        annotations.stream().filter(it->it instanceof CachePut).
                forEach(it-> ops.add(doPrasePut((CachePut) it,method,targetClass)));
        return Optional.ofNullable(ops);
    }
    @SuppressWarnings("Duplicates")
    protected CacheMetadata doPrasePut(CachePut cachePut, Method method, Class targetClass){
        CachePutMetadata cachePutMetadata =new CachePutMetadata();
        applyBase(cachePutMetadata,targetClass,method);
        cachePutMetadata.setKey(cachePut.key());
        cachePutMetadata.setPrefix(cachePut.prefix());
        cachePutMetadata.setCondition(cachePut.condition());
        cachePutMetadata.setTtl(cachePut.ttl());
        cachePutMetadata.setAnnotation(cachePut);
        return cachePutMetadata;
    }
    @SuppressWarnings("Duplicates")
    protected CacheMetadata doPraseCacheable(Cacheable cacheable, Method method, Class targetClass){
        CacheableMetadata cacheMetadata =new CacheableMetadata();
        applyBase(cacheMetadata,targetClass,method);
        cacheMetadata.setKey(cacheable.key());
        cacheMetadata.setPrefix(cacheable.prefix());
        cacheMetadata.setCondition(cacheable.condition());
        cacheMetadata.setTtl(cacheable.ttl());
        cacheMetadata.setAnnotation(cacheable);
        return cacheMetadata;
    }
    protected CacheMetadata doPraseEvict(CacheEvict cacheEvict,Method method, Class targetClass){
        CacheEvictMetadata cacheEvictMetadata=new CacheEvictMetadata();
        applyBase(cacheEvictMetadata,targetClass,method);
        cacheEvictMetadata.setKey(cacheEvict.key());
        cacheEvictMetadata.setPrefix(cacheEvict.prefix());
        cacheEvictMetadata.setCondition(cacheEvict.condition());
        cacheEvictMetadata.setAnnotation(cacheEvict);
        return cacheEvictMetadata;
    }
    private CacheMetadata applyBase(CacheMetadata cacheMetadata,Class clazz,Method method){
        cacheMetadata.setMethod(method);
        cacheMetadata.setParameterTypes(method.getParameterTypes());
        cacheMetadata.setClazz(clazz);
        return cacheMetadata;
    }



    @Getter
    @Setter
    static class CacheKey {
        private Method method;
        private Class  clazz;

        public CacheKey(Method method, Class clazz) {
            this.method = method;
            this.clazz = clazz;
        }

        @Override
        public boolean equals( Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CacheKey)) {
                return false;
            }
            CacheKey otherKey = (CacheKey) other;
            return (this.method.equals(otherKey.method) &&
                    ObjectUtils.nullSafeEquals(this.clazz, otherKey.clazz));
        }

        @Override
        public int hashCode() {
            return this.method.hashCode() + (this.clazz != null ? this.clazz.hashCode() * 29 : 0);
        }

        @Override
        public String toString() {
            return this.method + (this.clazz != null ? " on " + this.clazz : "");
        }
    }
}
