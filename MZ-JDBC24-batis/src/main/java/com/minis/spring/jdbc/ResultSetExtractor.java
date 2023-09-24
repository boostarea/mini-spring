package com.minis.spring.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetExtractor<T> {

	T extractData(ResultSet rs) throws SQLException;

}

