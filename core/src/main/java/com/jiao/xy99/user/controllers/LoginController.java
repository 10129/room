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
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 显示主界面.
     *
     * @param request
     *  HttpServletRequest
     */
//    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
//    @GetMapping(value = {"/", "/index.html"})
    @RequestMapping(value = {"/login.html", "/login"})
    public ModelAndView indexView(HttpServletRequest request) {
        return new ModelAndView("login");
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
        //redis 缓存
        redisTemplate.opsForHash().put("user", "username", "admin");
        redisTemplate.opsForHash().put("user", "password", "admin1");
        Object object = redisTemplate.opsForHash().get("user", "username");
        Object object2 = redisTemplate.opsForHash().get("user", "password");
        System.out.println(object);
        String view="login";
        if(isVaildUser){
            view="index";
        }
        return new ModelAndView(view);
    }
}
