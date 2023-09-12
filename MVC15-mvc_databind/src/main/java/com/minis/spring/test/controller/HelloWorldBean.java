package com.minis.spring.test.controller;

import com.minis.spring.beans.factory.annotation.Autowired;
import com.minis.spring.ioc_test.BaseService;
import com.minis.spring.test.User;
import com.minis.spring.web.RequestMapping;

public class HelloWorldBean {

	@Autowired
	BaseService baseservice;

	@RequestMapping("/test")
	public String doGet() {
		return "hello world!";
	}

	@RequestMapping("/test1")
	public String test1(User user) {
		return "" + user.getName() + user.getBirthday();
	}
}
