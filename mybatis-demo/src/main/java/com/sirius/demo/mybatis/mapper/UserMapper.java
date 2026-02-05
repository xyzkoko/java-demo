package com.sirius.demo.mybatis.mapper;

import com.sirius.demo.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 * 定义用户表的数据访问方法
 * @author Sirius
 */
public interface UserMapper {

    List<User> select();

    int insert(@Param("username") String username, @Param("password") String password);
}
