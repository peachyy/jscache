package com.peachyy.xcache.core;

import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.core.key.KeyGenerator;
import com.peachyy.xcache.core.spring.aspect.SpringElKeyGenerator;

/**
 * @author Xs.Tao
 */
public class DefaultKeyGenerator implements KeyGenerator {
    private  KeyGenerator keyGenerator;
    public DefaultKeyGenerator(){
        keyGenerator= new SpringElKeyGenerator();
    }
    public  DefaultKeyGenerator (KeyGenerator keyGenerator){
        this.keyGenerator=keyGenerator;
    }
    @Override
    public String generate(CacheMetadata metadata) {
        return keyGenerator.generate(metadata);
    }
}
