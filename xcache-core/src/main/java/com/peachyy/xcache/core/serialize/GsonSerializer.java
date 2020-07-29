package com.peachyy.xcache.core.serialize;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import com.peachyy.xcache.core.Serializer;

import java.io.IOException;

/**
 * @author Xs.Tao
 */
public class GsonSerializer implements Serializer {
    private Gson gson=null;
    public GsonSerializer(){
        gson=new Gson();
    }
    @Override
    public String name() {
        return "gson";
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        String json=gson.toJson(obj);
        return json.getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException {

        return null;
    }
}
