package com.minis.spring.test;

import com.minis.spring.web.RequestMapping;

public class HelloWorldBean {

	@RequestMapping("/test")
	public String doGet() {
		return "hello world!";
	}
}
