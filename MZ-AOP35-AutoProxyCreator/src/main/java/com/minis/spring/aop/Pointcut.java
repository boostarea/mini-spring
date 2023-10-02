package com.minis.spring.aop;

public interface Pointcut {
	//ClassFilter getClassFilter();

	MethodMatcher getMethodMatcher();

}
