package com.peachyy.xcache.core.serialize;

import com.peachyy.xcache.core.Serializer;
import com.peachyy.xcache.core.exception.CacheException;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Slf4j
public class SearializerUtils {
    private static Serializer serializer=null;

    private SearializerUtils(){

    }

    public static void init(String type){
        if(type==null || type.isEmpty()){
            type="FastJson";
        }
        switch (type){
            case "java":
                serializer=new JavaSerializer();
                break;
            case "stringUtf8":
                serializer=new StringUtf8Serializer();
                break;
            case "jackson":
                serializer=new JackJsonSerializer();
                break;
            case "gson":
                serializer=new GsonSerializer();
                break;
            case "FastJson":
                serializer=new FastJsonSerializer();
                break;
            default:
                throw new CacheException("cache searializer type error!");

        }
        log.info("use searializer {} {}",type,serializer.getClass().getName());
    }


    public static String name(){
        return serializer.name();
    }


    public static byte[] serialize(Object obj) {
        try {
            return serializer.serialize(obj);
        } catch (IOException e) {
            throw new CacheException("serialize error",e);
        }
    }


    public static Object deserialize(byte[] bytes)  {
        try {
            return serializer.deserialize(bytes);
        } catch (IOException e) {
            throw new CacheException("deserialize error",e);
        }
    }
}
