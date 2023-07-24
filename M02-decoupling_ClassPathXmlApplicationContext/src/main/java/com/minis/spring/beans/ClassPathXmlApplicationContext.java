package com.minis.spring.beans;

public class ClassPathXmlApplicationContext implements BeanFactory{
    BeanFactory beanFactory;
    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;

    }

    @Override
    public Object getBean(String name) throws BeansException {
        return this.beanFactory.getBean(name);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
