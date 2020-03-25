package com.lee.fateposter.filter;

import com.lee.fateposter.http.HttpInfo;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 13:05
 */
public class FirsterRequestFilter implements RequestFilter {
    @Override
    public HttpInfo filter(HttpInfo info) {
        return info;
    }
}
