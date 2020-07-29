package com.peachyy.xcache.core;

import org.springframework.util.ClassUtils;

import java.io.IOException;

/**
 * @author Xs.Tao
 */
public interface Serializer {

    String name();

    byte[] serialize(Object obj) throws IOException;


    Object deserialize(byte[] bytes) throws IOException ;

    default boolean canSerialize(Class<?> type) {
        return ClassUtils.isAssignable(getTargetType(), type);
    }

    default Class<?> getTargetType() {
        return Object.class;
    }
}
