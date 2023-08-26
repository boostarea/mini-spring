package com.minis.spring.beans.factory.support;

import com.minis.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected List<String> beanNames=new ArrayList<>();
    protected Map<String, Object> singletonObjects =new ConcurrentHashMap<>(256);

	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		synchronized(this.singletonObjects) {
	    	this.singletonObjects.put(beanName, singletonObject);
	    	this.beanNames.add(beanName);
		}
	}

	@Override
	public Object getSingleton(String beanName) {
		return this.singletonObjects.get(beanName);
	}

	@Override
	public boolean containsSingleton(String beanName) {
		return this.singletonObjects.containsKey(beanName);
	}

	@Override
	public String[] getSingletonNames() {
		return (String[]) this.beanNames.toArray();
	}
	
	protected void removeSingleton(String beanName) {
	    synchronized (this.singletonObjects) {
		    this.singletonObjects.remove(beanName);
		    this.beanNames.remove(beanName);
	    }
	}
}
