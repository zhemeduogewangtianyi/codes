package com.wty.maill.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;

public class FastJsonConvertUtil {

    private static final SerializerFeature[] serializerFeatures = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullStringAsEmpty
    };

    /**
     * 将JSON字符串转换为实体对象
     * */
    public static <T> T convertJSONToObject(String data,Class<T> clazz){
        try{
            T t = JSON.parseObject(data, clazz);
            return t;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSONObject对象转换为实体对象
     * */
    public static <T> T convertJSONToObject(JSONObject jsonObject,Class<T> clazz){
        try{
            T t = JSONObject.toJavaObject(jsonObject, clazz);
            return t;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSON字符串数组转换为List集合对象
     * */
    public static <T> List<T> convertJSONToArray(String data,Class<T> clazz){
        try{
            List<T> t = JSON.parseArray(data, clazz);
            return t;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将List<JSONObject>转换为List集合对象
     * */
    public static <T> List<T> convertJSONToArray(List<JSONObject> data,Class<T> clazz){
        try{
            List<T> list = new ArrayList<>();
            for(JSONObject obj : data){
                list.add(convertJSONToObject(obj,clazz));
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象转换为JSON字符串
     * */
    public static String convertObjectToJSON(Object object){
        try{
            String s = JSON.toJSONString(object);
            return s;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象转换为JSONObject对象
     * */
    public static JSONObject convertObjectToJSONObject(Object object){
        try{
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            return jsonObject;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 排除空的对象，返回json字符串
     * */
    public static String convertObjectToJSONWithNullValue(Object obj){
        try{
            String s = JSON.toJSONString(obj, serializerFeatures);
            return s;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
