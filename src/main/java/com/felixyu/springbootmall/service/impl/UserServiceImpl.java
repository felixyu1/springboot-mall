package com.felixyu.springbootmall.service.impl;

import com.felixyu.springbootmall.dao.UserDao;
import com.felixyu.springbootmall.dto.UserRegisterRequest;
import com.felixyu.springbootmall.model.User;
import com.felixyu.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
