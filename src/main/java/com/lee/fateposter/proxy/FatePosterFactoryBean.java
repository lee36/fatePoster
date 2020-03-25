package com.lee.fateposter.proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;
import java.util.Date;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 21:17
 */
public class FatePosterFactoryBean<T> implements FactoryBean<T>, BeanFactoryAware {

    private Class fatePosterInterface;
    private BeanFactory beanFactory;

    public FatePosterFactoryBean(Class  fatePosterInterface){
        this.fatePosterInterface=fatePosterInterface;
    }

    @Override
    public T getObject() throws Exception {
        FatePostProxy proxy =
                new FatePostProxy(fatePosterInterface,beanFactory);
        return (T)proxy.invoke();
    }

    @Override
    public Class<T> getObjectType() {
        return this.fatePosterInterface;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }
}
