package com.minis.spring.aop;

public interface MethodInterceptor extends Interceptor{
	Object invoke(MethodInvocation invocation) throws Throwable;
}
