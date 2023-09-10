package com.minis.spring.context;


import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.minis.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.spring.beans.factory.support.DefaultListableBeanFactory;
import com.minis.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.spring.core.ClassPathXmlResource;
import com.minis.spring.core.Resource;

import java.util.ArrayList;
import java.util.List;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext{
	DefaultListableBeanFactory beanFactory;
	private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors =
			new ArrayList<BeanFactoryPostProcessor>();

    public ClassPathXmlApplicationContext(String fileName){
    	this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
    	Resource res = new ClassPathXmlResource(fileName);
    	DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(res);
        
        this.beanFactory = bf;
        
        if (isRefresh) {
            try {
				refresh();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (BeansException e) {
				e.printStackTrace();
			}
        }
    }

	@Override
	void registerListeners() {
		ApplicationListener listener = new ApplicationListener();
		this.getApplicationEventPublisher().addApplicationListener(listener);
	
	}

	@Override
	void initApplicationEventPublisher() {
		ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
		this.setApplicationEventPublisher(aep);
	}

	@Override
	void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
	}

	@Override
	void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
		this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
	}

	@Override
	void onRefresh() {
		this.beanFactory.refresh();
	}

	@Override
	public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
		return this.beanFactory;
	}

	@Override
	public void addApplicationListener(ApplicationListener listener) {
		this.getApplicationEventPublisher().addApplicationListener(listener);
		
	}

	@Override
	void finishRefresh() {
		publishEvent(new ContextRefreshEvent("Context Refreshed..."));
		
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		this.getApplicationEventPublisher().publishEvent(event);
		
	}
   
    
}
