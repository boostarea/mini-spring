package com.minis.spring.beans;

public interface BeanFactory {
    Object getBean(String name) throws BeansException;

    Boolean containBean(String name);


    boolean isSingleton(String beanName);

    boolean isPrototype(String beanName);

    Class<?> getType(String beanName);
}
