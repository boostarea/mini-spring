package com.minis.spring.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();
    private List<String> beanDefinitionNames = new ArrayList<>();
//    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
//    private List<String> beanNames = new ArrayList<>();
//    private Map<String, Object> singletons = new HashMap<>();
    public SimpleBeanFactory(){}

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        if  (null == singleton) {
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
//            int i = beanNames.indexOf(beanName);
            if (null == beanDefinition) {
               throw new BeansException("beanName not found");
           } else {
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    throw new BeansException(e.getMessage());
                }
//                singletons.put(beanName, singleton);
                this.registerSingleton(beanName, singleton);
            }
        }
        return singleton;
    }

    @Override
    public Boolean containBean(String name) {
        return super.containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return this.beanDefinitions.get(beanName).isSingleton();
    }

    @Override
    public boolean isPrototype(String beanName) {
        return this.beanDefinitions.get(beanName).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.beanDefinitions.get(beanName).getClass();
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException {
//        this.beanDefinitions.add(beanDefinition);
//        this.beanNames.add(beanDefinition.getId());
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
        if (!beanDefinition.isLazyInit()) {
            try {
                this.getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitions.containsKey(beanName);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitions.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }
}
