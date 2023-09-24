package com.minis.spring.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementCallback {
	Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;
}
