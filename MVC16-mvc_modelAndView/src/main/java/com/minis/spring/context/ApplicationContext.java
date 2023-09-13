package com.minis.spring.context;


import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.factory.ListableBeanFactory;
import com.minis.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.minis.spring.beans.factory.config.ConfigurableBeanFactory;
import com.minis.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.spring.core.env.Environment;
import com.minis.spring.core.env.EnvironmentCapable;

/**
 * 所有的上下文都实现自
 * ApplicationContext，支持上下文环境和事件发布。
 */
public interface ApplicationContext
		extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher{
	String getApplicationName();
	long getStartupDate();
	ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
	void setEnvironment(Environment environment);
	Environment getEnvironment();
	void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);
	void refresh() throws BeansException, IllegalStateException;
	void close();
	boolean isActive();

}
