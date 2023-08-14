package com.minis.spring.beans;

import com.minis.spring.core.Resource;
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
            this.beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }
}
