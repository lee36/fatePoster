package com.lee.fateposter.test;

import com.lee.fateposter.annotation.Poster;

import java.util.Map;

public interface AppPoster {
    @Poster(url = "http://localhost:8081/app/client/handle_camera_status",header = {"Cookie","JSESSIONID=e4d73347-7280-4688-b891-5637e68d7818"})
    public String getMap(Map map);
}
