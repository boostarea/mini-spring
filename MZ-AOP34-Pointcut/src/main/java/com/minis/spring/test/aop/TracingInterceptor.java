package com.minis.spring.test.aop;

import com.minis.spring.aop.MethodInterceptor;
import com.minis.spring.aop.MethodInvocation;

public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation i) throws Throwable {
        System.out.println("method "+i.getMethod()+" is called on "+
                i.getThis()+" with args "+i.getArguments());
        Object ret=i.proceed();
        System.out.println("method "+i.getMethod()+" returns "+ret);
        return ret;
    }
}
