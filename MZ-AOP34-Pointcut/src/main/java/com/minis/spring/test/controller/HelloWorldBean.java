package com.minis.spring.test.controller;

import com.minis.spring.beans.factory.annotation.Autowired;
import com.minis.spring.ioc_test.BaseService;
import com.minis.spring.test.User;
import com.minis.spring.test.aop.IAction;
import com.minis.spring.web.RequestMapping;
import com.minis.spring.web.ResponseBody;
import com.minis.spring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

	@Autowired
	IAction action;

	@RequestMapping("/testaop")
	@ResponseBody
	public String doTestAop() {

		//DynamicProxy proxy = new DynamicProxy(action);
		//IAction p = (IAction)proxy.getProxy();
		System.out.println("action -------------- " + action + "----------------");

		action.doAction();

		return "test aop, hello world!";
	}

	@RequestMapping("/testaop2")
	public void doTestAop2(HttpServletRequest request, HttpServletResponse response) {
		action.doSomething();

		String str = "test aop, hello world!";
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
