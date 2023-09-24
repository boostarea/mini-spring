package com.minis.spring.web.servlet;


import com.minis.spring.beans.BeansException;
import com.minis.spring.http.convertor.HttpMessageConverter;
import com.minis.spring.web.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
	WebApplicationContext wac;
	private WebBindingInitializer webBindingInitializer = null;

	private HttpMessageConverter messageConverter = null;

	public RequestMappingHandlerAdapter() {
	}
	public RequestMappingHandlerAdapter(WebApplicationContext wac) {
		this.wac = wac;
		try {
			this.webBindingInitializer = (WebBindingInitializer)this.wac.getBean("webBindingInitializer");
		} catch (BeansException e) {
			throw new RuntimeException(e);
		}
	}

	public void setMessageConverter(HttpMessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}
	public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
		this.webBindingInitializer = webBindingInitializer;
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return handleInternal(request, response, (HandlerMethod) handler);
	}

	//从这个绑定过程中可以看到，循环过程就是按照参数在方法中出现的次序逐个绑定的，所 以这个次序是很重要的。
	private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
		return this.invokeHandlerMethod(request, response, handler);

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

	protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
		ModelAndView mav = null;
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
		if (invocableMethod.isAnnotationPresent(ResponseBody.class)) {
			this.messageConverter.write(returnObj, response);
		} else { //返回的是前端页面
			if (returnObj instanceof ModelAndView) {
				mav = (ModelAndView)returnObj;
			}
			else if(returnObj instanceof String) { //字符串也认为是前端页面
				String sTarget = (String)returnObj;
				mav = new ModelAndView();
				mav.setViewName(sTarget);
			}
		}
		return mav;

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
