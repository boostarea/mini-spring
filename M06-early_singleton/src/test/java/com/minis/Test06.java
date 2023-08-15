package com.minis;


import com.minis.cycle.AService;
import com.minis.spring.beans.BeansException;
import com.minis.spring.context.ClassPathXmlApplicationContext;

public class Test06 {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
//        BaseService aService = (BaseService) ctx.getBean("baseservice");
        AService aService = (AService) ctx.getBean("aservice");
        aService.sayHello();
    }
}
