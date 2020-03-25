package com.lee.fateposter.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.fateposter.http.HttpInfo;
import okhttp3.Call;
import okhttp3.Response;
import org.apache.catalina.User;

import java.io.IOException;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 13:18
 */
public class FirsterResponseConverter implements ResponseConverter {

    private ObjectMapper objectMapper=new ObjectMapper();
    @Override
    public Object converter(HttpInfo info, Response response) throws IOException {
        Class<?> type = info.getReponseType();
        if(Response.class==type){
            return response;
        }
        String json = response.body().string();
        if(String.class==type){
            return json;
        }
        Object result = objectMapper.readValue(json, type);
        return result;
    }
}
