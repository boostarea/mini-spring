package com.minis.spring.context;

import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.factory.BeanFactory;
import com.minis.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.minis.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.spring.core.ClassPathXmlResource;
import com.minis.spring.core.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory {
//    轻量的静态代理，未影响被代理类主功能
    AutowireCapableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName){
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
//        STEP_01 从fileName构造resource
        Resource resource = new ClassPathXmlResource(fileName);
//        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        AutowireCapableBeanFactory bf = new AutowireCapableBeanFactory();
//        Reader串联起BF和Resource， 完成xml->BD->内存映射
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = bf;
        if (isRefresh) {
            try {
                this.refresh();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void refresh() throws BeansException, IllegalStateException {
        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(this.beanFactory);

        // Initialize other special beans in specific context subclasses.
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory bf) {
        //if (supportAutowire) {
        bf.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
        //}
    }

    private void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public Object getBean(String name) throws BeansException {
//        BD -> Bean
        return this.beanFactory.getBean(name);
    }

}
