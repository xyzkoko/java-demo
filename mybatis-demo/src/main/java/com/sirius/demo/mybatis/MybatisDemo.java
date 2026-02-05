package com.sirius.demo.mybatis;

import com.sirius.demo.domain.User;
import com.sirius.demo.mybatis.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * MyBatis使用示例
 * 演示通过MyBatis执行增删改查操作
 * @author Sirius
 */
public class MybatisDemo {

    public static void main(String[] args) throws InterruptedException {
        MybatisConfig mybatisConfig = new MybatisConfig();
        // 使用完sqlSession后需要关闭
        try (SqlSession sqlSession = mybatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.insert("aaa","bbb");
            List<User> userList = mapper.select();
            System.out.println(userList);
            sleep(10000);
        }
    }
}
