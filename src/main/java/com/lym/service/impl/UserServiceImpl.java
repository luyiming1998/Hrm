package com.lym.service.impl;

import com.lym.dao.UserMapper;
import com.lym.entity.User;
import com.lym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public User Login(String loginName, String password) {
        return userMapper.Login(loginName,password);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectAllUser(User user,Integer start) {
        return userMapper.selectAllUser(user,start);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public User selectUpdateUser(Integer id) {
        return userMapper.selectUpdateUser(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePwd(Integer id, String password) {
        return userMapper.updatePwd(id,password);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int delUser(Integer id) {
        return userMapper.delUser(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int totalcountUser(User user) {
        return userMapper.totalcountUser(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int selectName(String loginname) {
        return userMapper.selectName(loginname);
    }

}
