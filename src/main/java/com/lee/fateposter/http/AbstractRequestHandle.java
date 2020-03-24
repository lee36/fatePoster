package com.lee.fateposter.http;

import okhttp3.Response;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 23:02
 */
public class AbstractRequestHandle implements RequestHandle, InitializingBean {

    @Override
    public Response handleHttp(Method method) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //init filter and some handles
    }
}
