package com.lee.fateposter.proxy;

import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Proxy;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 22:07
 */
public class FatePostProxy {

    private Class fatePosterInterface;
    private ClassLoader fatePosterClassLoder;
    private BeanFactory beanFactory;

    public FatePostProxy(Class fatePosterInterface,BeanFactory beanFactory){
        this.fatePosterInterface=fatePosterInterface;
        this.fatePosterClassLoder=fatePosterInterface.getClassLoader();
        this.beanFactory=beanFactory;
    }
    public Object invoke(){
        FatePosterInvocationHandler handler = beanFactory.getBean(FatePosterInvocationHandler.class);
        return  Proxy.newProxyInstance(fatePosterClassLoder,new Class[]{fatePosterInterface}
                , handler);
    }
}
