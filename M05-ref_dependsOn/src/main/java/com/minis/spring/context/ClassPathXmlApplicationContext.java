package com.minis.spring.context;

import com.minis.spring.beans.BeanFactory;
import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.SimpleBeanFactory;
import com.minis.spring.beans.XmlBeanDefinitionReader;
import com.minis.spring.core.ClassPathXmlResource;
import com.minis.spring.core.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory {
//    轻量的静态代理，未影响被代理类主功能
SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName){
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
//        STEP_01 从fileName构造resource
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
//        Reader串联起BF和Resource， 完成xml->BD->内存映射
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            this.beanFactory.refresh();
        }
    }

    @Override
    public Object getBean(String name) throws BeansException {
//        BD -> Bean
        return this.beanFactory.getBean(name);
    }

}
