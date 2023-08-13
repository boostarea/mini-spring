package com.minis.spring.beans;

import com.minis.spring.core.Resource;
import org.dom4j.Element;

public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;
    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource)  {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            try {
                this.beanFactory.registerBeanDefinition(beanDefinition);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
