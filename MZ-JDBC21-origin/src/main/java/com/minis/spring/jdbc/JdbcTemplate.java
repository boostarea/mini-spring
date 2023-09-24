package com.minis.spring.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 动静分离，将固定的套路作为模板定下来，变化的部分让子类重写
 * @author chen
 * @date 2023/09/24
 */
public abstract class JdbcTemplate {
    public Object query(String sql) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Object rtnObj = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=DEMO;user=sa;password=Sql2016;");
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            rtnObj = doInStatement(rs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {

            }
        }
        return rtnObj;
    }
    protected abstract  Object doInStatement(ResultSet rs);
}
