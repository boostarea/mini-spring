package com.minis.spring.beans.factory;

import com.minis.spring.beans.BeansException;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;
//    void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException;

    boolean containsBean(String name);
    boolean isSingleton(String name);
    boolean isPrototype(String name);
    Class<?> getType(String name);
}
