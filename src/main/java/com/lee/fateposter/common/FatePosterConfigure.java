package com.lee.fateposter.common;

import com.lee.fateposter.annotation.EnableFatePoster;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 7:47
 */
public class FatePosterConfigure implements BeanFactoryPostProcessor {

    private static final String ENABLE_FATE_POSTER_NAME = EnableFatePoster.class.getName();
    private static final Boolean USE_DEFAULT_FILTER=false;
    private AnnotationMetadata annotationMetadata;

    public FatePosterConfigure(AnnotationMetadata metadata){
        this.annotationMetadata=metadata;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                Map<String,Object> map = annotationMetadata.
                getAnnotationAttributes(ENABLE_FATE_POSTER_NAME);
        String packages = Optional.ofNullable(map).map((each) -> each.get("value"))
                .filter((str)->!StringUtils.isEmpty(str)).map((src)->(String)src)
                .orElseThrow(() -> new RuntimeException("please input the package src"));
        FatePosterBeanDefinitionScanner scanner =
                new FatePosterBeanDefinitionScanner((BeanDefinitionRegistry)beanFactory,
                        USE_DEFAULT_FILTER);
        scanner.scan(packages);
    }
}
