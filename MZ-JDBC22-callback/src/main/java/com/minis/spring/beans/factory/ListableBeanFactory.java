package com.minis.spring.beans.factory;


import com.minis.spring.beans.BeansException;

import java.util.Map;

/**
 * 将 Factory 内部管理的 Bean 作为一个集合来对待
 *
 * @author chen
 * @date 2023/08/30
 */
public interface ListableBeanFactory extends BeanFactory {

	boolean containsBeanDefinition(String beanName);

	int getBeanDefinitionCount();

	String[] getBeanDefinitionNames();

	String[] getBeanNamesForType(Class<?> type);

	<T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

}

