package com.lym.dao;

import com.lym.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper {

    User Login(@Param("loginname") String loginName, @Param("password") String password);

    List<User> selectAllUser(@Param("user") User user, @Param("start") Integer start);

    User selectUpdateUser(Integer id);

    int addUser(User user);

    int updateUser(User user);

    int updatePwd(@Param("id") Integer id,@Param("password") String password);

    int delUser(Integer id);

    int totalcountUser(User user);

    int selectName(String loginname);
}
