package com.peachyy.jscache.core;

import com.peachyy.jscache.common.CacheMetadata;
import com.peachyy.jscache.core.key.KeyGenerator;
import com.peachyy.jscache.core.key.SpringElKeyGenerator;

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

    @Override
    public String generate(CacheMetadata metadata, Object[] arguments) {
        return keyGenerator.generate(metadata,arguments);
    }

    @Override
    public boolean argCondition(CacheMetadata metadata, Object[] arguments) {
        return keyGenerator.argCondition(metadata,arguments);
    }

    @Override
    public boolean returnCondition(CacheMetadata metadata, Object returnValue) {
        return keyGenerator.returnCondition(metadata,returnValue);
    }


}
