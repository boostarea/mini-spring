package com.minis.spring.beans.factory.config;

import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.factory.BeanFactory;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

    void setBeanFactory(BeanFactory beanFactory);
}
