package com.minis.spring.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

public interface StatementCallback {
	Object doInStatement(Statement stmt) throws SQLException;
}
