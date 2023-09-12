package com.minis.spring.web.servlet;


import com.minis.spring.beans.BeansException;
import com.minis.spring.web.WebApplicationContext;
import com.minis.spring.web.WebBindingInitializer;
import com.minis.spring.web.WebDataBinder;
import com.minis.spring.web.WebDataBinderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
	WebApplicationContext wac;
	private WebBindingInitializer webBindingInitializer = null;

	public RequestMappingHandlerAdapter(WebApplicationContext wac) {
		this.wac = wac;
		try {
			this.webBindingInitializer = (WebBindingInitializer)this.wac.getBean("webBindingInitializer");
		} catch (BeansException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		handleInternal(request, response, (HandlerMethod) handler);
	}

	//从这个绑定过程中可以看到，循环过程就是按照参数在方法中出现的次序逐个绑定的，所 以这个次序是很重要的。
	private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
		this.invokeHandlerMethod(request, response, handler);

		//	Method method = handler.getMethod();
		//	Object obj = handler.getBean();
		//	Object objResult = null;
		//	try {
		//		objResult = method.invoke(obj);
		//	} catch (IllegalAccessException e) {
		//		e.printStackTrace();
		//	} catch (IllegalArgumentException e) {
		//		e.printStackTrace();
		//	} catch (InvocationTargetException e) {
		//		e.printStackTrace();
		//	}
		//
		//try {
		//	response.getWriter().append(objResult.toString());
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}

	protected void invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
		WebDataBinderFactory binderFactory = new WebDataBinderFactory();
		Parameter[] methodParameters = handler.getMethod().getParameters();
		Object[] methodParamObjs = new Object[methodParameters.length];
		int i=0;
		//对调用方法里的每一个参数，处理绑定
		for (Parameter methodParameter : methodParameters) {
			Object methodParamObj = methodParameter.getType().newInstance(); //给这个参数创建WebDataBinder
			WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
			wdb.bind(request);
			methodParamObjs[i] = methodParamObj;
			i++;
		}
		Method invocableMethod = handler.getMethod();
		Object returnObj = invocableMethod.invoke(handler.getBean(), methodParamObjs);
		response.getWriter().append(returnObj.toString());

		//	Method method = handler.getMethod();
		//	Object obj = handler.getBean();
		//	Object objResult = null;
		//	try {
		//		objResult = method.invoke(obj);
		//	} catch (IllegalAccessException e) {
		//		e.printStackTrace();
		//	} catch (IllegalArgumentException e) {
		//		e.printStackTrace();
		//	} catch (InvocationTargetException e) {
		//		e.printStackTrace();
		//	}
		//
		//try {
		//	response.getWriter().append(objResult.toString());
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}


}
