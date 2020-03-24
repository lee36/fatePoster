package com.lee.fateposter.common;

import com.lee.fateposter.annotation.EnableFatePoster;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 20:46
 */
public class FatePosterBeanRegister implements ImportBeanDefinitionRegistrar {

    private static final String ENABLE_FATE_POSTER_NAME = EnableFatePoster.class.getName();
    private static final Boolean USE_DEFAULT_FILTER=false;
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                        BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String,Object> map = annotationMetadata.
                getAnnotationAttributes(ENABLE_FATE_POSTER_NAME);
        String packages = Optional.ofNullable(map).map((each) -> each.get("value"))
                .filter((str)->!StringUtils.isEmpty(str)).map((src)->(String)src)
                .orElseThrow(() -> new RuntimeException("please input the package src"));
        FatePosterBeanDefinitionScanner scanner = 
                new FatePosterBeanDefinitionScanner(beanDefinitionRegistry,USE_DEFAULT_FILTER);
        scanner.scan(packages);
    }
}
