package com.minis.spring.beans.factory.support;


import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.minis.spring.beans.factory.config.BeanDefinition;
import com.minis.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 继承了其他 BeanFactory 类来实现 Bean 的创建 管理功能
 *
 * @author chen
 * @date 2023/08/30
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
					implements ConfigurableListableBeanFactory {
	ConfigurableListableBeanFactory parentBeanFactory;
	public void setParent(ConfigurableListableBeanFactory beanFactory) {
		this.parentBeanFactory = beanFactory;
	}

	@Override
	public int getBeanDefinitionCount() {
		return this.beanDefinitionMap.size();
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return (String[])this.beanDefinitionNames.toArray(new String[0]);
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type) {
		List<String> result = new ArrayList<>();

		for (String beanName : this.beanDefinitionNames) {
			boolean matchFound = false;
			BeanDefinition mbd = this.getBeanDefinition(beanName);
			Class<?> classToMatch = mbd.getClass();
			if (type.isAssignableFrom(classToMatch)) {
				matchFound = true;
			}
			else {
				matchFound = false;
			}

			if (matchFound) {
				result.add(beanName);
			}
		}
		return (String[]) result.toArray();

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		String[] beanNames = getBeanNamesForType(type);
		Map<String, T> result = new LinkedHashMap<>(beanNames.length);
		for (String beanName : beanNames) {
			Object beanInstance = getBean(beanName);
			result.put(beanName, (T) beanInstance);
		}
		return result;
	}

	//先从 WebApplicationContext 中获取，若为空则通过 parentApplicationContext 获取
	@Override
	public Object getBean(String beanName) throws BeansException{
		Object result = super.getBean(beanName);
		if (result == null) {
			result = this.parentBeanFactory.getBean(beanName);
		}
		return result;
	}

}
