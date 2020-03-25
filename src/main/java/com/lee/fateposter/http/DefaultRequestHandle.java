package com.lee.fateposter.http;

import okhttp3.Request;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 9:54
 */
public class DefaultRequestHandle extends AbstractRequestHandle{

    @Override
    public Request buildRequest(HttpInfo info) {
        List<MethodBuilder> builderList =
                getMethodBuilderList();
        for (MethodBuilder methodBuilder : builderList) {
            if(methodBuilder.support(info.getRequestMethod())){
                Request request = methodBuilder.buildRequest(info);
                return request;
            }
        }
        return null;
    }
}
