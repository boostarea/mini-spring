package com.minis.spring.aop;


import com.minis.spring.beans.BeansException;
import com.minis.spring.beans.factory.BeanFactory;
import com.minis.spring.beans.factory.FactoryBean;
import com.minis.spring.util.ClassUtils;

public class ProxyFactoryBean implements FactoryBean<Object> {
	private AopProxyFactory aopProxyFactory;
	private String[] interceptorNames;
	private String targetName;
	private Object target;
	private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
	private Object singletonInstance;

	private BeanFactory beanFactory;
	private String interceptorName;
	private Advisor advisor;
	
	public ProxyFactoryBean() {
		this.aopProxyFactory = new DefaultAopProxyFactory();
	}
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
		this.aopProxyFactory = aopProxyFactory;
	}
	public AopProxyFactory getAopProxyFactory() {
		return this.aopProxyFactory;
	}
	protected AopProxy createAopProxy() {
System.out.println("----------createAopProxy for :"+target+"--------");
		return getAopProxyFactory().createAopProxy(target, this.advisor);
	}


	public void setInterceptorNames(String... interceptorNames) {
		this.interceptorNames = interceptorNames;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}

	@Override
	public Object getObject() throws Exception {
		initializeAdvisor();
		return getSingletonInstance();
	}

	private synchronized Object getSingletonInstance() {
		if (this.singletonInstance == null) {
			this.singletonInstance = getProxy(createAopProxy());
		}
System.out.println("----------return proxy for :"+this.singletonInstance+"--------"+this.singletonInstance.getClass());

		return this.singletonInstance;
	}
	protected Object getProxy(AopProxy aopProxy) {
		return aopProxy.getProxy();
	}
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	private synchronized void initializeAdvisor() {
		Object advice = null;
		MethodInterceptor mi = null;
		try {
			advice = this.beanFactory.getBean(this.interceptorName);
		} catch (BeansException e) {
			e.printStackTrace();
		}
		if (advice instanceof BeforeAdvice) {
			mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice)advice);
		}
		else if (advice instanceof AfterAdvice){
			mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice)advice);
		}
		else if (advice instanceof MethodInterceptor) {
			mi = (MethodInterceptor)advice;
		}

		advisor = new DefaultAdvisor();
		advisor.setMethodInterceptor(mi);

	}

}
