package com.peachyy.jscache.core;

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
