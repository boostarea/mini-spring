package com.minis;

import com.minis.spring.ClassPathXmlApplicationContext;

public class Test01 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) ctx.getBean("aService");
        aService.sayHi();
    }
}
