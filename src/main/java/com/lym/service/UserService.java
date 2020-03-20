package com.lym.service;

import com.lym.entity.User;

import java.util.List;

public interface UserService {
    User Login(String loginName, String password);

    List<User> selectAllUser(User user, Integer start);

    User selectUpdateUser(Integer id);

    int addUser(User user);

    int updateUser(User user);

    int updatePwd(Integer id, String password);

    int delUser(Integer id);

    int totalcountUser(User user);

    int selectName(String loginname);
}
