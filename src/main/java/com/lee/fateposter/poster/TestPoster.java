package com.lee.fateposter.poster;

import com.lee.fateposter.annotation.Poster;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/27 0027 10:04
 */
public interface TestPoster {
    @Poster(url = "http://www.baidu.com")
    public String baidu();

    @Poster(url = "http://localhost:8080/testaa",method = RequestMethod.POST)
    public Map toMap(@RequestBody Map map);
}
