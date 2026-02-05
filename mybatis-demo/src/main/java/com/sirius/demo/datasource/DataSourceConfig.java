package com.sirius.demo.datasource;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

/**
 * DataSource数据源配置类
 * 使用MysqlDataSource管理数据库连接
 * @author Sirius
 */
public class DataSourceConfig {

    private MysqlDataSource mysqlDataSource;

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test01?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public DataSourceConfig() {
        this.mysqlDataSource = new MysqlDataSource();
        this.mysqlDataSource.setURL(DB_URL);
        this.mysqlDataSource.setUser(USER);
        this.mysqlDataSource.setPassword(PASS);
    }

    /**
     * 1、创建数据库连接
     * 使用DriverManager不需要显示的使用Class.forName()加载数据库驱动
     */
    public Connection getConnection() throws SQLException {
        return this.mysqlDataSource.getConnection();
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
