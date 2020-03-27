package com.lee.fateposter.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.fateposter.converter.FirsterRequestConverter;
import com.lee.fateposter.converter.RequestConverter;
import okhttp3.*;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Map;


/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 19:09
 */
public abstract class AbstractMethodBuilder implements MethodBuilder {

    private RequestConverter requestConverter;

    private ObjectMapper objectMapper=new ObjectMapper();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType FORM = MediaType.parse("application/json; charset=utf-8");

   public AbstractMethodBuilder(){
       this.requestConverter=new FirsterRequestConverter();
   }

    public RequestConverter getRequestConverter() {
        return requestConverter;
    }

    public Headers wrapHeader(Map<String,String> headers){
        Headers.Builder builder = new Headers.Builder();
        for (String key : headers.keySet()) {
            builder.add(key,headers.get(key));
        }
        return builder.build();
    }
    public HttpUrl wrapGetUrl(HttpInfo info) throws IllegalAccessException {
        HttpUrl.Builder urlBuilder =HttpUrl.parse(info.getUrl())
                .newBuilder();
        Map<String, Object> map = info.getNomalMap();
        for (String key : map.keySet()) {
            Object param = map.get(key);
            Class<?> aClass = param.getClass();
            if(isNotComplexClass(aClass)){
                urlBuilder.addQueryParameter(key,param+"");
            }else {
                Map<String,Object> isMap = checkIsMap(param);
                if(!CollectionUtils.isEmpty(isMap)){
                    for (String key1 : isMap.keySet()) {
                        urlBuilder.addQueryParameter(key1,isMap.get(key1)+"");
                    }
                }else {
                    Field[] fields = aClass.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        urlBuilder.addQueryParameter(field.getName(), field.get(param) + "");
                    }
                }
            }
        }
        return urlBuilder.build();
    }

    public RequestBody wrapRequestBody(HttpInfo info) throws Exception {
        String json = checkRequestBody(info);
        if(json!=null){
            return RequestBody.create(json,JSON);
        }
        FormBody.Builder builder = new FormBody.Builder();
        Map<String, Object> map = info.getNomalMap();
        for (String key : map.keySet()) {
            Object param = map.get(key);
            Class<?> aClass = param.getClass();
            if(isNotComplexClass(aClass)){
                builder.add(key,param+"");
            }else {
                Map<String,Object> isMap = checkIsMap(param);
                if(!CollectionUtils.isEmpty(isMap)){
                    for (String key1 : isMap.keySet()) {
                        builder.add(key1,isMap.get(key1)+"");
                    }
                }else{
                    Field[] fields = aClass.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        builder.add(field.getName(), field.get(param) + "");
                    }
                }
            }
        }
       return builder.build();
    }

    private String checkRequestBody(HttpInfo info) throws JsonProcessingException {
        Map<String, Object> map = info.getNomalMap();
        for (String key : map.keySet()) {
            Object value = map.get(key);
            Map<String, Parameter> parameterMap =
                    info.getParameterMap();
            //如果存在requestBody
            if(parameterMap.get(key).isAnnotationPresent(org.springframework.web.bind.annotation.RequestBody.class)){
                return objectMapper.writeValueAsString(value);
            }
        }
        return null;
    }

    private boolean isNotComplexClass(Class<?> aClass) {
       if(int.class==aClass||Integer.class==aClass){
           return true;
       }else if(long.class==aClass||Long.class==aClass){
           return true;
       }else if(double.class==aClass||Double.class==aClass){
            return true;
        }else if(short.class==aClass||Short.class==aClass){
           return true;
       }else if(String.class==aClass){
           return true;
       }
       return false;
    }
    public Map<String,Object> checkIsMap(Object value){
       if(value instanceof Map){
          return (Map<String,Object>) value;
       }
       return null;
    }
}
