package com.lee.fateposter.config;

import com.lee.fateposter.http.DefaultRequestHandle;
import com.lee.fateposter.proxy.FatePosterInvocationHandler;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 13:53
 */
@Configuration
@EnableConfigurationProperties(OkHttpProperties.class)
public class FatePostConfiguration {

    @Bean
    public DefaultRequestHandle requestHandle(){
        return new DefaultRequestHandle();
    }

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient client(OkHttpProperties properties){
        OkHttpClient.Builder builder =
                new OkHttpClient.Builder();
        builder.connectTimeout(properties.getConnectTimeout())
                .readTimeout(properties.getReadTimeout())
                .writeTimeout(properties.getWriteTimeout())
        .callTimeout(properties.getCallTimeout());
        return builder.build();
    }
    @Bean
    public FatePosterInvocationHandler handler(){
        return new FatePosterInvocationHandler();
    }
}
