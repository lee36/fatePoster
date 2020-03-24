package com.lee.fateposter.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 21:41
 */
public class FatePosterInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //deal your self logic
        try {
            return method.invoke(this, args);
        }catch (Exception e){}
            return "123";
    }

}
