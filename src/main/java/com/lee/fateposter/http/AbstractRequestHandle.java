package com.lee.fateposter.http;

import com.lee.fateposter.annotation.Poster;
import com.lee.fateposter.converter.FirsterResponseConverter;
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
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static final String REGEX= "\\{([^}]*)\\}";

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
        buildPath(info);
        Request request = buildRequest(info);
        //全局拦截器
        Request passRquest=request;
        for (RequestFilter requestFilter : requestFilterList) {
            Request temp = requestFilter.filter(passRquest);
            passRquest=temp;
        }
        if(!Objects.isNull(info.getUniqueRequestFilter())){
            passRquest=info.getUniqueRequestFilter().uniqueFilter(passRquest);
        }
        OkHttpClient httpClient = beanFactory.getBean(OkHttpClient.class);
        if(Objects.isNull(httpClient)){
            throw new RuntimeException("please insure at last a instance which is OkHttpClient");
        }
        Call call = httpClient.newCall(passRquest);
        Response response = null;
        try {
            response = call.execute();
            Object result = responseConverter.converter(info, response);
            return result;
        } catch (IOException e) {
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

    public void buildPath(HttpInfo info){
        if(!info.getPathVarible()){
            info.setUrl(info.getAnnotation().url());
        }else{
            info.setUrl(wrapPathVarible(info.getAnnotation(),info.getPathVaribleMap()));
        }
        Optional.ofNullable(info.getUrl()).filter((str)-> !StringUtils.isEmpty(str))
                .orElseThrow(()->new RuntimeException("please input the url"));
    }

    private String wrapPathVarible(Poster poster, Map<String,Object> params) {
        String url = poster.url();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(url);
        while (matcher.find ()) {
            String varibles=matcher.group();
            Object value = params.get(varibles);
            url = url.replace(varibles, value + "");
        }
        return url;
    }
}
