package com.minis.spring.web;


import com.minis.spring.context.ApplicationContext;

import javax.servlet.ServletContext;

public interface WebApplicationContext extends ApplicationContext {
	/**
	 *Servlet 容器本身的上下文
	 */
	String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

	ServletContext getServletContext();
	void setServletContext(ServletContext servletContext);
}
