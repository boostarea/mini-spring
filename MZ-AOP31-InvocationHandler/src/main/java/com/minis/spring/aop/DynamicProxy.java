package com.minis.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//这种方式显然是不美观的，需要在业务逻辑程序中写上，对代码的侵入性太强
public class DynamicProxy {
    private Object subject = null;

    public DynamicProxy(Object subject) {
        this.subject = subject;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(DynamicProxy.class.getClassLoader(), subject.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                        if (method.getName().equals("doAction")) {
                            System.out.println("before call real object........");
                            return method.invoke(subject, args);
                        }
                        return null;
                    }
                });
    }

    public static void main(String[] args) {
        IAction action = new Action1();
        DynamicProxy proxy = new DynamicProxy(action);

        IAction proxy1 = (IAction) proxy.getProxy();
        proxy1.doAction();
    }
}
