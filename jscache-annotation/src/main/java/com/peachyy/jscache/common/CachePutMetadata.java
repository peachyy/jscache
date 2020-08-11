package com.peachyy.jscache.common;

import com.peachyy.jscache.annation.CachePut;

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
