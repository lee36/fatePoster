package com.lee.fateposter.http;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lee.fateposter.annotation.Poster;
import com.lee.fateposter.common.FatePosterConfigure;
import com.lee.fateposter.proxy.FatePosterInvocationHandler;
import org.springframework.boot.autoconfigure.security.servlet.AntPathRequestMatcherProvider;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 10:15
 */
public class HttpInfo {

    private String url;
    private RequestMethod requestMethod;
    private Map<String,String> hearders;
    private Class<?> reponseType;
    private Map<String,Object> nomalMap;
    private Map<String,Object> pathVaribleMap;
    private Boolean isPathVarible;
    private Map<String,Parameter> parameterMap;

    public static final String REGEX= "\\{([^}]*)\\}";
    public static final Class<PathVariable> PATH_VARIBLE_CLASS= PathVariable.class;


    public HttpInfo(Method method,List<Object> args){
       wrapInfo(method,args);
    }


    private void wrapInfo(Method method, List<Object> args) {
        Poster annotation =
                method.getAnnotation(FatePosterInvocationHandler.POSTER_CLASS);
        this.hearders = wrapHeader(annotation.header());
        this.requestMethod = annotation.method();
        wrapParams(method,args);
        this.reponseType=wrapReponse(method);
        this.isPathVarible=inferPathVarible(annotation.url());
        if(!isPathVarible){
            this.url = annotation.url();
        }else{
            this.url= wrapPathVarible(annotation,pathVaribleMap);
        }
        Optional.ofNullable(this.url).filter((str)-> !StringUtils.isEmpty(str))
                .orElseThrow(()->new RuntimeException("please input the url"));
    }

    public Map<String, Parameter> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Parameter> parameterMap) {
        this.parameterMap = parameterMap;
    }

    private void wrapParams(Method method, List<Object> args) {
        Map<String, Object> nomalMap = Maps.newHashMap();
        Map<String, Object> pathVaribleMap = Maps.newHashMap();
        Map<String, Parameter> parameterMap = Maps.newHashMap();
        Parameter[] parameters = method.getParameters();
        for (int i=0;i<parameters.length;i++){
            if(!parameters[i].isAnnotationPresent(PATH_VARIBLE_CLASS)){
                nomalMap.put(parameters[i].getName(),args.get(i));
                parameterMap.put(parameters[i].getName(),parameters[i]);
            }else{
                pathVaribleMap.put("{"+parameters[i].getName()+"}",args.get(i));
            }
        }
        this.nomalMap=nomalMap;
        this.pathVaribleMap=pathVaribleMap;
        this.parameterMap=parameterMap;
    }

    private String wrapPathVarible(Poster poster,Map<String,Object> params) {
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

    private Boolean inferPathVarible(String url) {
        AntPathMatcher matcher =
                new AntPathMatcher();
        boolean result = matcher.isPattern(url);
        return result;
    }

    public void setPathVarible(Boolean pathVarible) {
        isPathVarible = pathVarible;
    }

    public Boolean getPathVarible() {
        return isPathVarible;
    }

    private Class<?> wrapReponse(Method method) {
        return method.getReturnType();
    }

    private Map<String, String> wrapHeader(String[] header) {
        Map<String,String> map = new HashMap<>();
        for(int i=0;i<header.length/2;i++){
            map.put(header[i*2],header[i*2+1]);
        }
        return map;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, String> getHearders() {
        return hearders;
    }

    public void setHearders(Map<String, String> hearders) {
        this.hearders = hearders;
    }

    public Class<?> getReponseType() {
        return reponseType;
    }

    public void setReponseType(Class<?> reponseType) {
        this.reponseType = reponseType;
    }

    @Override
    public String toString() {
        return "HttpInfo{" +
                "url='" + url + '\'' +
                ", requestMethod=" + requestMethod +
                ", hearders=" + hearders +
                ", reponseType=" + reponseType +
                ", nomalMap=" + nomalMap +
                ", pathVaribleMap=" + pathVaribleMap +
                ", isPathVarible=" + isPathVarible +
                '}';
    }

    public Map<String, Object> getNomalMap() {
        return nomalMap;
    }

    public void setNomalMap(Map<String, Object> nomalMap) {
        this.nomalMap = nomalMap;
    }

    public Map<String, Object> getPathVaribleMap() {
        return pathVaribleMap;
    }

    public void setPathVaribleMap(Map<String, Object> pathVaribleMap) {
        this.pathVaribleMap = pathVaribleMap;
    }
}
