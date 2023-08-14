package com.minis;


import com.minis.spring.beans.BeansException;
import com.minis.spring.context.ClassPathXmlApplicationContext;

public class Test03 {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) ctx.getBean("aService");
        aService.sayHi();
    }
}
