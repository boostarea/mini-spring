package com.minis.spring.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
//    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private Map<String,BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames=new ArrayList<>();
    public SimpleBeanFactory(){}

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        if  (null == singleton) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new BeansException(e.getMessage());
            }
            this.registerBean(beanName, singleton);

//            int i = beanNames.indexOf(beanName);
//            if (-1 == i) {
//               throw new BeansException("beanName not found");
//           } else {
//                BeanDefinition beanDefinition = beanDefinitions.get(i);
//                try {
//                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
//                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
//                    throw new BeansException(e.getMessage());
//                }
//                this.registerBean(beanName, singleton);
//            }
        }
        return singleton;
    }

    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.containsSingleton(name);
    }
}
