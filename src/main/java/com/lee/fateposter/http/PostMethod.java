package com.lee.fateposter.http;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 14:26
 */
public class PostMethod extends AbstractMethodBuilder{
    @Override
    public Request buildRequest(HttpInfo info) throws Exception {
        Request.Builder builder =
                new Request.Builder();
        Headers headers = wrapHeader(info.getHearders());
        RequestBody body=wrapRequestBody(info);
        Request request = builder.url(info.getUrl()).
                headers(headers).post(body).build();
        return request;
    }


    @Override
    public Boolean support(RequestMethod method) {
        if(method==RequestMethod.POST){
            return true;
        }
        return false;
    }
}
