package com.minis.spring.beans;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;
    void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException;
}
