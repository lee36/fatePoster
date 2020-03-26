package com.lee.fateposter;


import com.google.common.collect.ImmutableMap;
import com.lee.fateposter.annotation.EnableFatePoster;
import com.lee.fateposter.test.AppPoster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@SpringBootApplication
@EnableFatePoster("com.lee.fateposter.test")
@RestController
public class FatePosterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FatePosterApplication.class, args);
    }

}
