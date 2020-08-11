package com.peachyy.jscache.common;

import com.peachyy.jscache.annation.Cacheable;

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
