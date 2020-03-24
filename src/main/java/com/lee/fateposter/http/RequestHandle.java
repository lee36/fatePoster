package com.lee.fateposter.http;

import okhttp3.Response;

import java.lang.reflect.Method;

public interface RequestHandle {
    public Response handleHttp(Method method);
}
