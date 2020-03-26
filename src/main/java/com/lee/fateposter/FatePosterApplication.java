package com.lee.fateposter;

import com.google.common.collect.ImmutableMap;
import com.lee.fateposter.annotation.EnableFatePoster;
import com.lee.fateposter.test.MyTest;
import com.lee.fateposter.test.User;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableFatePoster("com.lee.fateposter.test")
public class FatePosterApplication implements ApplicationRunner{
    @Autowired
    private MyTest studentTest;

    public static void main(String[] args) {
        SpringApplication.run(FatePosterApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user=new User();
        user.setAge(18);
        user.setName("dasdasd");
        Response test = studentTest
                .test(1,user);
        System.out.println(test);
    }
}
