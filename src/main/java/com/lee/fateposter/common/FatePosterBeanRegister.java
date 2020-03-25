package com.lee.fateposter.common;

import com.lee.fateposter.annotation.EnableFatePoster;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
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

    private static final String FATE_POSTER_CONFIGURE_NAME = FatePosterConfigure.class.getName();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                        BeanDefinitionRegistry beanDefinitionRegistry) {

        AnnotatedGenericBeanDefinition definition = new
                AnnotatedGenericBeanDefinition(FatePosterConfigure.class);
        definition.getConstructorArgumentValues().addGenericArgumentValue(annotationMetadata);
        AnnotationConfigUtils.processCommonDefinitionAnnotations(definition);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(definition,FATE_POSTER_CONFIGURE_NAME);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder,beanDefinitionRegistry);
    }
}
