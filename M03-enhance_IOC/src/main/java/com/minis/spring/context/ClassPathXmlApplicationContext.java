package com.minis.spring.context;

import com.minis.spring.beans.BeanFactory;
import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.SimpleBeanFactory;
import com.minis.spring.beans.XmlBeanDefinitionReader;
import com.minis.spring.core.ClassPathXmlResource;
import com.minis.spring.core.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    BeanFactory beanFactory;
    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;

    }

    @Override
    public Object getBean(String name) throws BeansException {
        return this.beanFactory.getBean(name);
    }

    @Override
    public Boolean containBean(String name) {
        return this.beanFactory.containBean(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) throws BeansException {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return false;
    }

    @Override
    public boolean isPrototype(String beanName) {
        return false;
    }

    @Override
    public Class<?> getType(String beanName) {
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
