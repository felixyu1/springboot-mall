package com.felixyu.springbootmall.dto;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class UserRegisterRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
