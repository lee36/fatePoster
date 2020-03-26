package com.lee.fateposter;


import com.google.common.collect.ImmutableMap;
import com.lee.fateposter.annotation.EnableFatePoster;
import com.lee.fateposter.test.AppPoster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@SpringBootApplication
@EnableFatePoster("com.lee.fateposter.test")
@RestController
public class FatePosterApplication implements ApplicationRunner {
    @Autowired
    private AppPoster appPoster;
    @RequestMapping("/app")
    public Map getMap(){
        return ImmutableMap.of("name","zhangsan");
    }

    public static void main(String[] args) {
        SpringApplication.run(FatePosterApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String map = appPoster.getMap();
        System.out.println(map);
    }
}
