package com.lee.fateposter.annotation;

import com.lee.fateposter.common.FatePosterBeanRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 20:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FatePosterBeanRegister.class)
public @interface EnableFatePoster {
    public String value() default "";
}
