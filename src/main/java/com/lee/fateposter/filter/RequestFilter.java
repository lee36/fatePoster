package com.lee.fateposter.filter;

import com.lee.fateposter.http.HttpInfo;
import okhttp3.Request;

public interface RequestFilter {
    public void filter(Request request);
}
