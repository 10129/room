package com.jiao.xy99.user.dto;

import java.util.Date;

/**
 * 用户
 * Created by xieshuai on 2018/4/7.
 */
public class User1 {

    private String username;
    //加密过的密码
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}