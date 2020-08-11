package com.peachyy.jscache.common;

import java.io.Serializable;
import java.lang.reflect.Method;

import lombok.Getter;
import lombok.Setter;

/**
 * 缓存原数据
 * @author Xs.Tao
 */
@Getter
@Setter
public class CacheMetadata implements Serializable {
    private Class clazz;
    private Method method;
    private Class<?>[] parameterTypes;
    private Object[] arguments;
    //
    private String prefix;
    private String key;
    private String condition;


    //ann

}
