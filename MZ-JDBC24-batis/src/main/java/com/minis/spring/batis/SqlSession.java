package com.minis.spring.batis;


import com.minis.spring.jdbc.JdbcTemplate;
import com.minis.spring.jdbc.PreparedStatementCallback;

public interface SqlSession {
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);
	Object selectOne(String sqlid, Object[] args, PreparedStatementCallback pstmtcallback);
}
