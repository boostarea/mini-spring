package com.minis.spring.test;


import com.minis.spring.jdbc.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserJdbcImpl extends JdbcTemplate {

	@Override
	protected Object doInStatement(ResultSet rs) {
		User rtnUser = null;
		
		try {
			if (rs.next()) {
				rtnUser = new User();
				rtnUser.setId(rs.getInt("id"));
				rtnUser.setName(rs.getString("name"));
				rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
			} else {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rtnUser;
	}

}
