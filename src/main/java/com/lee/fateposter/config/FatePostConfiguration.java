package com.lee.fateposter.config;

import com.lee.fateposter.http.DefaultRequestHandle;
import com.lee.fateposter.proxy.FatePosterInvocationHandler;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 13:53
 */
@Configuration
public class FatePostConfiguration {

    @Bean
    public DefaultRequestHandle requestHandle(){
        return new DefaultRequestHandle();
    }

    @Bean
    public OkHttpClient client(){
        OkHttpClient.Builder builder =
                new OkHttpClient.Builder();
        return builder.build();
    }
    @Bean
    public FatePosterInvocationHandler handler(){
        return new FatePosterInvocationHandler();
    }
}
