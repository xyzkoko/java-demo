package com.sirius.demo.jdbc;

import java.sql.*;

/**
 * JDBC配置类
 * 使用DriverManager管理数据库连接
 * @author Sirius
 */
public class JDBCConfig {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test01?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456";

    /**
     * 1、创建数据库连接
     * 使用DriverManager不需要显示的使用Class.forName()加载数据库驱动
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * 2、创建执行SQL语句的对象
     */
    public Statement getStatement(Connection conn) throws SQLException {
        return conn.createStatement();
    }

    /**
     * 3、执行SQL语句
     */
    public ResultSet getResultSet(Statement stmt, String sql) throws SQLException {
        return stmt.executeQuery(sql);
    }
}
