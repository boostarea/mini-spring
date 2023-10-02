
package com.minis.spring.aop;

public interface PointcutAdvisor extends Advisor {
	Pointcut getPointcut();
}