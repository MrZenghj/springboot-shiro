package com.example.demo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

public class JsonUtils {

    // 对象转json
    public static String toJson(Object object, ObjectMapper objectMapper) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
    //json字符串转对象
    public static <T> T parseJson(String json, TypeReference<T> typeReference, ObjectMapper objectMapper)
            throws Exception{
        if(StringUtils.isEmpty(json)){
            return null;
        }
        return objectMapper.readValue(json,typeReference);
    }
    public static <T> T parseJson(String json,Class<T> cls ,ObjectMapper objectMapper)
            throws Exception{
        if(StringUtils.isEmpty(json)){
            return null;
        }
        return objectMapper.readValue(json,cls);
    }

}
