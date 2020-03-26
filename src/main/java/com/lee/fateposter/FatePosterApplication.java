package com.lee.fateposter;


import com.lee.fateposter.annotation.EnableFatePoster;
import com.lee.fateposter.test.AppPoster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableFatePoster("com.lee.fateposter.test")
@RestController
public class FatePosterApplication implements ApplicationRunner {
    @Autowired
    private AppPoster poster;
    public static void main(String[] args) {
        SpringApplication.run(FatePosterApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String,String> map
                = new HashMap<>();
        map.put("key","8dc7959eeee2792ac2eebb490e60deed1");
        map.put("stationId","1");
        map.put("clientId","2");
        map.put("cameraId","0306d8ded17648d6bc093b040cf9f18c");
        String s = poster.getMap(map);
        System.out.println(s);
    }
}
