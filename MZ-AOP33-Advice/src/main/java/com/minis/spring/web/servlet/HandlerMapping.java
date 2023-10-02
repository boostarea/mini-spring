package com.minis.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
	HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
