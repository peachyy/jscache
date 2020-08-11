package com.peachyy.xcache.common;

import com.peachyy.xcache.annation.CachePut;
import com.peachyy.xcache.annation.Cacheable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xs.Tao
 */
@Getter
@Setter
public class CachePutMetadata extends CacheMetadata {
    private     CachePut  annotation;
    private long      ttl;
    private     boolean   allowNullValue;
}
