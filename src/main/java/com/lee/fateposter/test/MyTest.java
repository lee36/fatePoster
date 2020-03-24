package com.lee.fateposter.test;

import com.lee.fateposter.annotation.Poster;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 23:11
 */
public interface MyTest {
    @Poster(url = "/app/test",method = RequestMethod.DELETE,header = {})
    public Object test();
}
