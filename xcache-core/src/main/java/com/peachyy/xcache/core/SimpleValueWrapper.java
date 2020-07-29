package com.peachyy.xcache.core;

/**
 * @author Xs.Tao
 */
public class SimpleValueWrapper implements Cache.ValueWrapper {
    private Object value;
    @Override
    public Object get() {
        return value;
    }
    public SimpleValueWrapper(Object value){
        this.value=value;
    }
}
