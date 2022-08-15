package com.felixyu.springbootmall.service.impl;

import com.felixyu.springbootmall.dao.UserDao;
import com.felixyu.springbootmall.dto.UserLoginRequest;
import com.felixyu.springbootmall.dto.UserRegisterRequest;
import com.felixyu.springbootmall.model.User;
import com.felixyu.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.DESKeySpec;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查註冊e-mail
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if(user != null){
            log.warn("該e-mail{} 已註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5生成密碼雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(
                userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 檢查user是否存在
        if(user == null){
            log.warn("該e-mail{} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5生成密碼雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(
                userLoginRequest.getPassword().getBytes());

        // 比較密碼
        if(user.getPassword().equals(hashedPassword)){
            return user;
        }else{
            log.warn("該e-mail{}的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
