package com.lee.fateposter.proxy;

import java.lang.reflect.Proxy;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 22:07
 */
public class FatePostProxy {

    private Class fatePosterInterface;
    private ClassLoader fatePosterClassLoder;

    public FatePostProxy(Class  fatePosterInterface){
        this.fatePosterInterface=fatePosterInterface;
        this.fatePosterClassLoder=fatePosterInterface.getClassLoader();
    }
    public Object invoke(){
        return  Proxy.newProxyInstance(fatePosterClassLoder,new Class[]{fatePosterInterface}
                , new FatePosterInvocationHandler());
    }
}
