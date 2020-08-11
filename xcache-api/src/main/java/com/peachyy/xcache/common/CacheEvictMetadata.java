package com.peachyy.xcache.common;

import com.peachyy.xcache.annation.CacheEvict;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xs.Tao
 */
@Getter
@Setter
public class CacheEvictMetadata extends CacheMetadata {
    private CacheEvict annotation;
}
