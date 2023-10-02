package com.minis.spring.web.servlet;

public interface ViewResolver {
	View resolveViewName(String viewName) throws Exception;
}
