package com.peachyy.xcache.core.serialize;

import com.peachyy.xcache.core.Serializer;
import com.peachyy.xcache.core.exception.CacheException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Xs.Tao
 */
public class JavaSerializer implements Serializer {
    @Override
    public String name() {
        return "java";
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(obj);
            return baos.toByteArray();
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException {
        if(bytes == null || bytes.length == 0)
            return null;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(bais)){
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new CacheException(e);
        }
    }
}
