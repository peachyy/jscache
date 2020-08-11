package com.peachyy.jscache.core.key;

import com.peachyy.jscache.common.CacheMetadata;

/**
 * @author Xs.Tao
 */
public interface KeyGenerator {

    /**
     *
     * @param metadata
     * @return
     */
    String  generate(CacheMetadata metadata);

    String generate(CacheMetadata metadata,Object[] arguments);
}
