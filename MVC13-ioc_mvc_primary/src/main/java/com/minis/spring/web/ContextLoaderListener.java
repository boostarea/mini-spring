package com.minis.spring.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
	public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
	private WebApplicationContext context;
	
	public ContextLoaderListener() {
	}
	
	public ContextLoaderListener(WebApplicationContext context) {
		this.context = context;
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		initWebApplicationContext(event.getServletContext());
	}

	private void initWebApplicationContext(ServletContext servletContext) {
		String sContextLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
		System.out.println("sContextLocation-----------" + sContextLocation);
		WebApplicationContext wac = new AnnotationConfigWebApplicationContext(sContextLocation);
		wac.setServletContext(servletContext);
		this.context = wac;

		//Servlet 容器本身的上下文
		//AnnotationConfigWebApplicationContext 和 servletContext 就能够互相引用了，很方便
		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
	}
	

}
