package com.minis.spring.test;


import com.minis.spring.jdbc.JdbcTemplate;

public class UserService {
	public User getUserInfo(int userid) {
		String sql = "select id, name,birthday from users where id="+userid;
		JdbcTemplate jdbcTemplate = new UserJdbcImpl();
		User rtnUser = (User)jdbcTemplate.query(sql);
		return rtnUser;
	}

}
