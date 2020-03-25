package com.lee.fateposter.proxy;


import com.lee.fateposter.annotation.Poster;
import com.lee.fateposter.http.HttpInfo;
import com.lee.fateposter.http.RequestHandle;
import javafx.geometry.Pos;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 21:41
 */
public class FatePosterInvocationHandler implements InvocationHandler, BeanFactoryPostProcessor {

    public static final Class<Poster> POSTER_CLASS= Poster.class;
    private BeanFactory beanFactory;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //deal your self logic
        try {
            return method.invoke(this, args);
        }catch (Exception e){}
        HttpInfo info = new HttpInfo(method, Arrays.asList(args));
        RequestHandle handle = beanFactory.getBean(RequestHandle.class);
        return handle.handleHttp(info);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }
}
