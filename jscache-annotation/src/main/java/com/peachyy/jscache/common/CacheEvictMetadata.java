package com.peachyy.jscache.common;

import com.peachyy.jscache.annation.CacheEvict;

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
