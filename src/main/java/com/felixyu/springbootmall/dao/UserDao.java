package com.felixyu.springbootmall.dao;

import com.felixyu.springbootmall.dto.UserRegisterRequest;
import com.felixyu.springbootmall.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
