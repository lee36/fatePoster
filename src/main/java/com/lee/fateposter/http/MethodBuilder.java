package com.lee.fateposter.http;


import okhttp3.Request;
import org.springframework.web.bind.annotation.RequestMethod;

public interface MethodBuilder {
    public Request buildRequest(HttpInfo info) throws Exception;
    public Boolean support(RequestMethod method);
}
