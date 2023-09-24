package com.minis.spring.test.controller;

import com.minis.spring.beans.factory.annotation.Autowired;
import com.minis.spring.ioc_test.BaseService;
import com.minis.spring.test.User;
import com.minis.spring.web.RequestMapping;
import com.minis.spring.web.ResponseBody;
import com.minis.spring.web.servlet.ModelAndView;

import java.util.Date;

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

	@RequestMapping("/test7")
	@ResponseBody
	public User doTest7(User user) {
		user.setName(user.getName() + "---");
		user.setBirthday(new Date());
		return user;
	}

	@RequestMapping("/test5")
	public ModelAndView doTest5(User user) {
		ModelAndView mav = new ModelAndView("test","msg",user.getName());
		return mav;
	}
}
