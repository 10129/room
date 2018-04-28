package com.jiao.xy99.user.service;

import com.jiao.xy99.system.util.ResponseData;
import com.jiao.xy99.user.dto.User;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author xieshuai
 * @date 2018/4/8
 */
public interface ILoginService {

//    public boolean hasMatchUser(String name, String password);

    public User findUserByUsername(String username);

    /**
     * 验证session中的登录信息
     */
    public boolean validateSessionLogin(HttpServletRequest request);
    /**
     * 登录
     */
    public ResponseData login(HttpServletRequest request, String username, String password, String myVerifyCode, String time);
    /**
     * 注册
     */
    public ResponseData register(HttpServletRequest request,
                                  String username,
                                  String password,
                                  String name,
                                  String email,
                                  String rcode,
                                  String tm) ;
}