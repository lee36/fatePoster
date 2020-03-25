package com.lee.fateposter.http;


import okhttp3.Request;
import org.springframework.web.bind.annotation.RequestMethod;

public interface MethodBuilder {
    public Request buildRequest(HttpInfo info);
    public Boolean support(RequestMethod method);
}
