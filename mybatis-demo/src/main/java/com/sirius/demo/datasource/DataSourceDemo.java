package com.sirius.demo.datasource;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sirius.demo.jdbc.JDBCConfig;
import com.sirius.demo.domain.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DataSource数据源使用示例
 * 演示通过DataSource获取连接并查询数据库
 * @author Sirius
 */
public class DataSourceDemo {

    public static void main(String[] args) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        ResultSet resultSet = null;
        try (Connection connection = dataSourceConfig.getConnection();
             Statement statement = dataSourceConfig.getStatement(connection)) {
            String sql = "select * from user";
            resultSet = dataSourceConfig.getResultSet(statement, sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User(id, username, password);
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
