package com.minis;


import com.minis.spring.beans.BeansException;
import com.minis.spring.context.ClassPathXmlApplicationContext;

public class Test02 {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) ctx.getBean("aService");
        aService.sayHi();
    }
}
