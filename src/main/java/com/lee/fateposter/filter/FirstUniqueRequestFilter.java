package com.lee.fateposter.filter;


import okhttp3.Request;


/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/26 0026 10:41
 */
public class FirstUniqueRequestFilter implements UniqueRequestFilter {
    @Override
    public Request uniqueFilter(Request request) {
        return request;
    }
}
