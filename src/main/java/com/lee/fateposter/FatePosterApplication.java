package com.lee.fateposter;


import com.lee.fateposter.annotation.EnableFatePoster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.HashMap;

@SpringBootApplication
@EnableFatePoster("com.lee.fateposter.test")
public class FatePosterApplication{


    public static void main(String[] args) {
        SpringApplication.run(FatePosterApplication.class, args);
    }

}
