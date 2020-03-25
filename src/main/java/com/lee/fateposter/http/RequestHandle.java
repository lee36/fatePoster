package com.lee.fateposter.http;

import okhttp3.Response;

import java.lang.reflect.Method;
import java.util.Objects;

public interface RequestHandle {
    public Object handleHttp(HttpInfo info);
}
