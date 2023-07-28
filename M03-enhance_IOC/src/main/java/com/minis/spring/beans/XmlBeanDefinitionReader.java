package com.minis.spring.beans;

import org.dom4j.Element;

public class XmlBeanDefinitionReader {
    SimpleBeanFactory beanFactory;
    public XmlBeanDefinitionReader(SimpleBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource)  {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            try {
                beanFactory.registerBeanDefinition(beanDefinition);
//                this.beanFactory.registerBean(beanId, beanDefinition);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
