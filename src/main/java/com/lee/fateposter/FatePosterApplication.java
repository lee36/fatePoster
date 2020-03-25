package com.lee.fateposter;

import com.lee.fateposter.annotation.EnableFatePoster;
import com.lee.fateposter.test.MyTest;
import com.lee.fateposter.test.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        User user = new User();
        user.setAge(18);
        user.setName("zhangsan");
        String test = studentTest
                .test(user);
        System.out.println(test);
    }
}
