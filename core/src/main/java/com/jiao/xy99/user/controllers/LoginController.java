package com.jiao.xy99.user.controllers;

/**
 * Created by xieshuai on 2018/4/7.
 */

import javax.servlet.http.HttpServletRequest;

import com.jiao.xy99.system.util.ResponseData;
import com.jiao.xy99.user.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ILoginService loginService;


    /**
     * 显示主界面.
     *
     * @param request
     *  HttpServletRequest
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView indexView(HttpServletRequest request) {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(HttpServletRequest request,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("myVerifyCode") String myVerifyCode,
                              @RequestParam("time") String time) {
        ResponseData responseData=loginService.login( request,  username,  password,  myVerifyCode,  time);
        return responseData;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData register(HttpServletRequest request,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("rcode") String rcode,
                              @RequestParam("tm") String tm) {
        ResponseData responseData=loginService.register( request, username, password, name, email, rcode, tm) ;
        return responseData;
    }

    @RequestMapping(value = "/login_success", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView loginSuccess(HttpServletRequest request) {
        Boolean isVaildUser=loginService.validateSessionLogin(request);
        String view="index";
        if(isVaildUser){
            view="desk_index";
        }
        return new ModelAndView(view);
    }
}
