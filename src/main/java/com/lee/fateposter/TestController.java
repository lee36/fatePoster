package com.lee.fateposter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.fateposter.test.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 20:53
 */
@RestController
public class TestController {
    @RequestMapping("/app/test")
    public User test(User user){
        return user;
    }
}
