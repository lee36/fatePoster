package com.lee.fateposter.test;

import com.lee.fateposter.annotation.Poster;
import okhttp3.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 23:11
 */
public interface MyTest {
    @Poster(url = "http://localhost:8080/app/test/",method = RequestMethod.POST,header = {"aa","123","bb","321"})
    public String test(@RequestBody User map);
}
