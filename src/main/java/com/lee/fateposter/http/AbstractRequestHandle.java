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
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.io.IOException;
import java.util.*;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 23:02
 */
public abstract class AbstractRequestHandle implements RequestHandle,BeanFactoryPostProcessor {

     private List<RequestFilter> requestFilterList;
     private ResponseConverter responseConverter;
     private List<MethodBuilder> methodBuilderList;
     private ConfigurableListableBeanFactory beanFactory;

     public AbstractRequestHandle(){
         requestFilterList=new ArrayList<>();
         methodBuilderList=new ArrayList<>();
     }

    private void initRequestFilter(BeanFactory factory) {
       //加载内置
        requestFilterList.add(new FirsterRequestFilter());
       //加载用户自定义
       //SPI 以及 component
       loadSPI(RequestFilter.class,requestFilterList);
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

    private void initResponseConverter(BeanFactory factory) {
        //加载内置
        responseConverter=new FirsterResponseConverter();
        //加载用户自定义
        try {
            ResponseConverter converter = beanFactory.getBean(ResponseConverter.class);
            responseConverter=converter;
        }catch (NoSuchBeanDefinitionException e){}
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
        initRequestFilter(beanFactory);
        initResponseConverter(beanFactory);
        initMethodBuilder();
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
        Response response = null;
        try {
            response = call.execute();
            //responseConverter
            Object result = responseConverter.converter(info, response);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("exec fail");
        }
    }
    public abstract Request buildRequest(HttpInfo info);

    public List<RequestFilter> getRequestFilterList() {
        return requestFilterList;
    }

    public void setRequestFilterList(List<RequestFilter> requestFilterList) {
        this.requestFilterList = requestFilterList;
    }

    public ResponseConverter getResponseConverter() {
        return responseConverter;
    }

    public void setResponseConverter(ResponseConverter responseConverter) {
        this.responseConverter = responseConverter;
    }

    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public List<MethodBuilder> getMethodBuilderList() {
        return methodBuilderList;
    }

    public void setMethodBuilderList(List<MethodBuilder> methodBuilderList) {
        this.methodBuilderList = methodBuilderList;
    }
}
