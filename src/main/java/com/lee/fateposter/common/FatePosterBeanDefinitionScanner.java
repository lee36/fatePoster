package com.lee.fateposter.common;

import com.lee.fateposter.proxy.FatePosterFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/24 0024 20:48
 */
public class FatePosterBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private static final Class FACTORY_BEAN=FatePosterFactoryBean.class;

    private static final Integer PUBLIC_SIGN=1537;

    public FatePosterBeanDefinitionScanner(BeanDefinitionRegistry registry,Boolean useDefaultFilter){
        super(registry,useDefaultFilter);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> scanBeanDefinitions = super.doScan(basePackages);
        //扫描后对BeanDefinitions进行改造
        Set<BeanDefinitionHolder> beans = scanBeanDefinitions.stream().map((bd) -> {
            AbstractBeanDefinition definition = (AbstractBeanDefinition) bd.getBeanDefinition();
            String sourceName = definition.getBeanClassName();
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            definition.getConstructorArgumentValues().addGenericArgumentValue(sourceName);
            definition.setBeanClass(FACTORY_BEAN);
            return bd;
        }).collect(Collectors.toSet());
        return beans;
    }

    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        return true;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        ScannedGenericBeanDefinition genericBeanDefinition
                =(ScannedGenericBeanDefinition)beanDefinition;
        String beanName = genericBeanDefinition.getBeanClassName();
        Class<?> aClass = null;
        try {
            aClass = Class.forName(beanName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("no such bean");
        }

        return aClass.isInterface()||aClass.getModifiers()==PUBLIC_SIGN;
    }
}
