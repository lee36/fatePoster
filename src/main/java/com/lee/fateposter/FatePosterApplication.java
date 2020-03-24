package com.lee.fateposter;

import com.lee.fateposter.annotation.EnableFatePoster;
import com.lee.fateposter.test.MyTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

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
        Object test = studentTest.test();
        System.out.println(test);
    }
}
