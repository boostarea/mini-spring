package com.minis.spring.beans.factory.config;


import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
	int AUTOWIRE_NO = 0;
	int AUTOWIRE_BY_NAME = 1;
	int AUTOWIRE_BY_TYPE = 2;

	Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException;

	Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
			throws BeansException;

}
