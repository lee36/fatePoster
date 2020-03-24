package com.lee.fateposter.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Poster {
    public String url();
    public RequestMethod method() default RequestMethod.GET;
    public String[] header() default {};
}
