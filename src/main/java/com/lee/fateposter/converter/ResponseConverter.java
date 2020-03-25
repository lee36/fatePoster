package com.lee.fateposter.converter;

import com.lee.fateposter.http.HttpInfo;
import okhttp3.Response;

import java.io.IOException;

public interface ResponseConverter {
    public Object converter(HttpInfo info, Response response) throws IOException;
}
