package com.felixyu.springbootmall.service;

import com.felixyu.springbootmall.dto.UserLoginRequest;
import com.felixyu.springbootmall.dto.UserRegisterRequest;
import com.felixyu.springbootmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);

    User getUserById(Integer userId);
}
