package com.peachyy.xcache.core.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peachyy.xcache.core.Serializer;

import java.io.IOException;

/**
 * @author Xs.Tao
 */
public class JackJsonSerializer implements Serializer {
    private ObjectMapper objectMapper=null;
    public JackJsonSerializer(){
        objectMapper=new ObjectMapper();
    }
    @Override
    public String name() {
        return "jackson";
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return objectMapper.writeValueAsBytes(obj);
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes,Object.class);
    }
}
