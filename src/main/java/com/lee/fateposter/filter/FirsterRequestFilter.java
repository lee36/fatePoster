package com.lee.fateposter.filter;

import okhttp3.Request;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 13:05
 */
public class FirsterRequestFilter implements RequestFilter {
    public Request filter(Request request){
       return request;
    }
}
