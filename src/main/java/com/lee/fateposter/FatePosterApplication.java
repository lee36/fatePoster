package com.lee.fateposter;


import com.google.common.collect.ImmutableMap;
import com.lee.fateposter.annotation.EnableFatePoster;
import com.lee.fateposter.poster.TestPoster;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@SpringBootApplication
@RestController
@EnableFatePoster("com.lee.fateposter.poster")
public class FatePosterApplication implements ApplicationRunner {
   @Autowired
   private TestPoster poster;
    public static void main(String[] args) {
        SpringApplication.run(FatePosterApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String baidu = poster.baidu();
        Map map = poster.toMap(ImmutableMap.of("code",200,
                "msg","success"));
        System.out.println(baidu);
        System.out.println(map);
    }
    @RequestMapping("/testaa")
    public Map toMap(@RequestBody Map map){
        return map;
    }
}
