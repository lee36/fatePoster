package com.lee.fateposter.filter;

import okhttp3.Request;

public interface UniqueRequestFilter {
    public Request uniqueFilter(Request request);
}
