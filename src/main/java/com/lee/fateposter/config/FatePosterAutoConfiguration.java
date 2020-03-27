package com.lee.fateposter.config;

import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/26 0026 8:08
 */
@Configuration
@ConditionalOnClass(OkHttpClient.class)
@Import(FatePostConfiguration.class)
public class FatePosterAutoConfiguration {

}
