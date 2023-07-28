package com.minis.spring.beans;

public interface BeanFactory {
    Object getBean(String name) throws BeansException;

    Boolean containBean(String name);

    void registerBean(String beanName, Object obj) throws BeansException;


    boolean isSingleton(String beanName);

    boolean isPrototype(String beanName);

    Class<?> getType(String beanName);
}
