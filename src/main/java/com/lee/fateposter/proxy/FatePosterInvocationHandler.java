package com.lee.fateposter.proxy;


import com.google.common.collect.Lists;
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
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 21:41
 */
public class FatePosterInvocationHandler implements InvocationHandler, BeanFactoryPostProcessor {

    public static final Class<Poster> POSTER_CLASS= Poster.class;
    private BeanFactory beanFactory;
    private ConcurrentHashMap<String,HttpInfo> httpInfos=new ConcurrentHashMap();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //deal your self logic
        try {
            return method.invoke(this, args);
        }catch (Exception e){}
        String id=getUniqueId(method,args);
        HttpInfo info=null;
        if(httpInfos.containsKey(id)){
            info = httpInfos.get(id);
        }else {
            if (Objects.isNull(args)) {
                info = new HttpInfo(method, Lists.newArrayList());
            } else{
                info = new HttpInfo(method, Arrays.asList(args));
            }
            httpInfos.put(id,info);
        }
        RequestHandle handle = beanFactory.getBean(RequestHandle.class);
        return handle.handleHttp(info);
    }

    private String getUniqueId(Method method, Object[] args) {
        if (args==null) {
          return method.toString()+"";
        }
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            builder.append(arg+"");
        }
        return method.toString()+builder.toString();
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }
}
