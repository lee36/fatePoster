package com.lee.fateposter.http;

import com.lee.fateposter.converter.FirsterRequestConverter;
import com.lee.fateposter.converter.FirsterResponseConverter;
import com.lee.fateposter.converter.RequestConverter;
import com.lee.fateposter.converter.ResponseConverter;
import com.lee.fateposter.filter.FirsterRequestFilter;
import com.lee.fateposter.filter.RequestFilter;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.*;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 23:02
 */
public abstract class AbstractRequestHandle implements RequestHandle,BeanFactoryPostProcessor {

     private List<RequestFilter> requestFilterList;
     private List<RequestConverter> requestConverterList;
     private List<ResponseConverter> responseConverterList;
     private List<MethodBuilder> methodBuilderList;
     private ConfigurableListableBeanFactory beanFactory;

     public AbstractRequestHandle(){
         requestFilterList=new ArrayList<>();
         requestConverterList= new ArrayList<>();
         responseConverterList=new ArrayList<>();
     }

    private void initRequestFilter(BeanFactory factory) {
       //加载内置
        requestFilterList.add(new FirsterRequestFilter());
       //加载用户自定义
       //SPI 以及 component
       loadSPI(RequestFilter.class,requestConverterList);
       loadFromIOC(RequestFilter.class,factory,requestFilterList);
    }

     private void loadFromIOC(Class aclass, BeanFactory factory, List list) {
         ListableBeanFactory listableBeanFactory=(ListableBeanFactory)factory;
         String[] names = listableBeanFactory.getBeanNamesForType(aclass);
         for (String name : names) {
             list.add(listableBeanFactory.getBean(name));
         }
     }

    private void loadSPI(Class aclass, List list) {
        ServiceLoader serviceLoader =
                ServiceLoader.load(aclass);
        Iterator iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            Object next = iterator.next();
            list.add(next);
        }
    }

    private void initRequestConverter(BeanFactory factory) {
        //加载内置
        requestConverterList.add(new FirsterRequestConverter());
        //加载用户自定义
        //SPI 以及 component
        loadSPI(RequestConverter.class,requestConverterList);
        loadFromIOC(RequestConverter.class,factory,requestConverterList);
    }
    private void initResponseConverter(BeanFactory factory) {
        //加载内置
        responseConverterList.add(new FirsterResponseConverter());
        //加载用户自定义
        //SPI 以及 component
        loadSPI(ResponseConverter.class,responseConverterList);
        loadFromIOC(ResponseConverter.class,factory,responseConverterList);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        initRequestFilter(beanFactory);
        initRequestConverter(beanFactory);
        initResponseConverter(beanFactory);
        initMethodBuilder();
        this.beanFactory=beanFactory;
    }

    private void initMethodBuilder() {
        methodBuilderList.add(new GetMethod());
        methodBuilderList.add(new PostMethod());
    }

    @Override
    public Object handleHttp(HttpInfo info) {
        Request request = buildRequest(info);
        //有必要可以用拦截器做一些修改
        for (RequestFilter requestFilter : requestFilterList) {
            requestFilter.filter(request);
        }
        OkHttpClient httpClient = beanFactory.getBean(OkHttpClient.class);
        if(Objects.isNull(httpClient)){
            throw new RuntimeException("please insure at last a instance which is OkHttpClient");
        }
        Call call = httpClient.newCall(request);
        return null;
    }
    public abstract Request buildRequest(HttpInfo info);

    public List<RequestFilter> getRequestFilterList() {
        return requestFilterList;
    }

    public void setRequestFilterList(List<RequestFilter> requestFilterList) {
        this.requestFilterList = requestFilterList;
    }

    public List<RequestConverter> getRequestConverterList() {
        return requestConverterList;
    }

    public void setRequestConverterList(List<RequestConverter> requestConverterList) {
        this.requestConverterList = requestConverterList;
    }

    public List<ResponseConverter> getResponseConverterList() {
        return responseConverterList;
    }

    public void setResponseConverterList(List<ResponseConverter> responseConverterList) {
        this.responseConverterList = responseConverterList;
    }

    public List<MethodBuilder> getMethodBuilderList() {
        return methodBuilderList;
    }

    public void setMethodBuilderList(List<MethodBuilder> methodBuilderList) {
        this.methodBuilderList = methodBuilderList;
    }
}
