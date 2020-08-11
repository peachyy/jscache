package com.peachyy.jscache.core.serialize;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.peachyy.jscache.core.Serializer;

import java.io.IOException;

/**
 * @author Xs.Tao
 */
public class JackJsonSerializer implements Serializer {
    private ObjectMapper objectMapper=null;
    public JackJsonSerializer(){
        objectMapper=new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
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
