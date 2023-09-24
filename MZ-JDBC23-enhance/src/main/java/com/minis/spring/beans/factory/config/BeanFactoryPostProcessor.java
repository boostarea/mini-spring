package com.minis.spring.beans.factory.config;


import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {
	void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
