package com.lee.fateposter.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.lee.fateposter.test.User;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Map;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 20:53
 */
@RestController
public class TestController {
    @RequestMapping("/app/test/{id}")
    public User test(@PathVariable Integer id, User user){
        return user;
    }
}
