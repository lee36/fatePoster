package com.lee.fateposter.filter;


import okhttp3.Request;

public interface RequestFilter {
    public Request filter(Request request);
}
