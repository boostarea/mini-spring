package com.minis.spring.aop;

//将动态添加的逻辑设计得更加结构化一点，而不是 全部简单地堆在 invoke() 一个方法中
//插入的例行性逻辑现在都单独抽取成一个部件了，应用程序员只要简单地实现
//整个软件结构化很好，完全解耦。
public interface BeforeAdvice extends Advice {

}
