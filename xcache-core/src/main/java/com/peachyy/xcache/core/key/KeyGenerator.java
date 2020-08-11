package com.peachyy.xcache.core.key;

import com.peachyy.xcache.common.CacheMetadata;

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
