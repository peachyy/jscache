package com.peachyy.xcache.core.serialize;

import com.peachyy.xcache.core.Serializer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @author Xs.Tao
 */
public class StringUtf8Serializer implements Serializer {
    @Override
    public String name() {
        return "stringUtf8";
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return Objects.toString(obj).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException {
        return new String(bytes, Charset.forName("UTF-8"));
    }
}
