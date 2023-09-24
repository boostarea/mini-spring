package com.minis.spring.jdbc;

import java.sql.*;

/**
 * 动静分离，将固定的套路作为模板定下来，变化的部分让子类重写
 * -
 * @author chen
 * @date 2023/09/24
 */
public abstract class JdbcTemplate {

    public Object query(StatementCallback stmtcallback) {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=DEMO;user=sa;password=Sql2016;");
            stmt = con.createStatement();

            return stmtcallback.doInStatement(stmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
                con.close();
            } catch (Exception e) {

            }
        }
        return null;
    }

    public Object query(String sql, Object[] args, PreparedStatementCallback pstmtcallback) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=DEMO;user=sa;password=Sql2016;");

            pstmt = con.prepareStatement(sql);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof String) {
                        pstmt.setString(i+1, (String)arg);
                    }
                    else if (arg instanceof Integer) {
                        pstmt.setInt(i+1, (int)arg);
                    }
                    else if (arg instanceof java.util.Date) {
                        pstmt.setDate(i+1, new java.sql.Date(((java.util.Date)arg).getTime()));
                    }
                }
            }
            return pstmtcallback.doInPreparedStatement(pstmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {

            }
        }
        return null;
    }

    protected abstract  Object doInStatement(ResultSet rs);
}
