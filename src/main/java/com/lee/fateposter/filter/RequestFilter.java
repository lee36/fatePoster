package com.lee.fateposter.filter;

import com.lee.fateposter.http.HttpInfo;

public interface RequestFilter {
    public HttpInfo filter(HttpInfo info);
}
