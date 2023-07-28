package com.minis.spring.beans;

import lombok.Data;

@Data
public class BeanDefinition {
    private String id;
    private String className;



    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    private boolean lazyInit = true;

//    dependsOn 属性记录 Bean 之间的依赖关系
    private String[] dependsOn;

    private ArgumentValues constructorArgumentValues;

    private PropertyValues propertyValues;

//    当一个 Bean 构造好并实例化之后是否要 让框架调用初始化方法
    private String initMethodName;

    private volatile Object beanClass;

    private String scope = SCOPE_SINGLETON;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }

    public void setConstructorArgumentValues(ArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues =
                (constructorArgumentValues != null ? constructorArgumentValues : new ArgumentValues());
    }
}
