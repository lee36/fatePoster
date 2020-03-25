package com.lee.fateposter.http;

import com.lee.fateposter.converter.FirsterRequestConverter;
import com.lee.fateposter.converter.FirsterResponseConverter;
import com.lee.fateposter.converter.RequestConverter;
import com.lee.fateposter.converter.ResponseConverter;
import com.lee.fateposter.filter.FirsterRequestFilter;
import com.lee.fateposter.filter.RequestFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.*;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 23:02
 */
public class AbstractRequestHandle implements RequestHandle, InitializingBean, BeanFactoryPostProcessor {

     private List<RequestFilter> requestFilterList;
     private List<RequestConverter> requestConverterList;
     private List<ResponseConverter> responseConverterList;

     private BeanFactory factory;

     public AbstractRequestHandle(){
         requestFilterList=new ArrayList<>();
         requestConverterList= new ArrayList<>();
         responseConverterList=new ArrayList<>();
     }


    @Override
    public void afterPropertiesSet() throws Exception {
        //init filter and some handles
        initRequestFilter(factory);
        initRequestConverter(factory);
        initResponseConverter(factory);
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
        this.factory=beanFactory;
    }

    @Override
    public Object handleHttp(HttpInfo info) {
        return null;
    }
}
