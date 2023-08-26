package com.minis.spring.beans.factory;

import com.minis.spring.beans.BeansException;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;
//    void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException;
}
