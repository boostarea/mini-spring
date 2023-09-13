package com.minis.spring.beans.factory.config;


import com.minis.spring.beans.factory.ListableBeanFactory;

/**
 * interface segregation
 * 一个 Interface 代表的是一种特性或者能力，我们把这些特性或能力 一个个抽取出来，各自独立互不干扰。
 * @author chen
 * @date 2023/08/30
 */
public interface ConfigurableListableBeanFactory
		extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

}
