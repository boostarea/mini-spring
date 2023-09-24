package com.minis.spring.batis;

public interface SqlSessionFactory {
	SqlSession openSession();
	MapperNode getMapperNode(String name);
}
