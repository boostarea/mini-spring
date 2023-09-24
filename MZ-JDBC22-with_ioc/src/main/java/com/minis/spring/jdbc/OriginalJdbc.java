package com.minis.spring.jdbc;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OriginalJdbc {

    @SneakyThrows
    public static void main(String[] args) {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=DEMO;user=sa;password=Sql2016;");
        PreparedStatement stmt = con.prepareStatement("select id from user");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong("id"));
        }

        rs.close();
        stmt.close();
        con.close();
    }
}
