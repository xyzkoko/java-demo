package com.sirius.demo.mybatis;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.sirius.demo.mybatis.mapper.UserMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * MyBatis配置类
 * 通过代码方式配置SqlSessionFactory
 * @author Sirius
 */
public class MybatisConfig {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test01?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public SqlSessionFactory sqlSessionFactory;

    private DataSource getDataSource() {
        // 使用JDBC自带的DataSource
        MysqlDataSource mysqlDataSource = new MysqlConnectionPoolDataSource();
        mysqlDataSource.setURL(DB_URL);
        mysqlDataSource.setUser(USER);
        mysqlDataSource.setPassword(PASS);
        // 使用Mybatis的PooledDataSource创建
        PooledDataSource pooledDataSource = new PooledDataSource(JDBC_DRIVER, DB_URL, USER, PASS);
        return mysqlDataSource;
    }

    public synchronized SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory != null) {
            return sqlSessionFactory;
        }
        DataSource dataSource = this.getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }
}
