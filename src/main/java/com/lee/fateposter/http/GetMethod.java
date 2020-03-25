package com.lee.fateposter.http;

import okhttp3.Request;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 14:26
 */
public class GetMethod implements MethodBuilder{
    @Override
    public Request buildRequest(HttpInfo info) {
        return null;
    }

    @Override
    public Boolean support(RequestMethod method) {
        if(method==RequestMethod.GET){
            return true;
        }
        return false;
    }
}
