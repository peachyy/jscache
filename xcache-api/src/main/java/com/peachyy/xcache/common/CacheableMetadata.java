package com.peachyy.xcache.common;

import com.peachyy.xcache.annation.Cacheable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xs.Tao
 */
@Getter
@Setter
public class CacheableMetadata extends CacheMetadata {
    private long ttl;
    private boolean allowNullValue;
    private Cacheable annotation;
}
