package com.minis.spring.aop;

public interface Advisor {
	MethodInterceptor getMethodInterceptor();
	void setMethodInterceptor(MethodInterceptor methodInterceptor);
	Advice getAdvice();
}
