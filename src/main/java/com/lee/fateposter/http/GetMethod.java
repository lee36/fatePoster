package com.lee.fateposter.http;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 14:26
 */
public class GetMethod extends AbstractMethodBuilder{
    @Override
    public Request buildRequest(HttpInfo info) throws Exception{
        Request.Builder builder =
                new Request.Builder();
        Headers headers = wrapHeader(info.getHearders());
        HttpUrl url = wrapGetUrl(info);
        Request request = builder.get().
                url(url).headers(headers).build();
        return request;
    }


    @Override
    public Boolean support(RequestMethod method) {
        if(method==RequestMethod.GET){
            return true;
        }
        return false;
    }
}
