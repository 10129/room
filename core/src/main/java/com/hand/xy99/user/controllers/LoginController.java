package com.hand.xy99.user.controllers;

/**
 * Created by xieshuai on 2018/4/7.
 */
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hand.xy99.system.ResponseData;
import com.hand.xy99.system.VerifyCode.VerifyCode;
import com.hand.xy99.user.dto.LoginCommand;
import com.hand.xy99.user.dto.User;
import com.hand.xy99.user.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ILoginService loginService;

//    @RequestMapping(value = "/login")
//    public String loginPage() {
//        return "login.jsp";
//    }
    /**
     * 显示主界面.
     *
     * @param request
     *            HttpServletRequest
     */
//    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
//    public ModelAndView indexView(HttpServletRequest request) {
//        return new ModelAndView("login");
//    }
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(HttpServletRequest request,
                                     @RequestParam("dtocode") String dtocode,
                                     @RequestParam("time") String time) {
        ResponseData responseData = new ResponseData();
        String code="S";
        boolean success=true;
        String message=null;
        //获取参数
        String verifyCode=(String)request.getSession().getAttribute("code");
        String[] user=dtocode.split(",");
        String username=user[0];
        String password=user[1];
        String myVerifyCode=user[2];
        //验证码
        if(!myVerifyCode.toLowerCase().equals(verifyCode.toLowerCase())){
            success=false;
            code="codeerror";
        }
        LoginCommand loginCommand =new LoginCommand();
        boolean isVaildUser = loginService.hasMatchUser( username, password );
        logger.debug( "开始登录。。。"+username );
        if (!isVaildUser) {
            code="E";
            success=false;
            message= "用户名或密码错误!" ;
        } else {
            User user1 = loginService.findUserByUsername( username );
            user1.setLastIp( request.getLocalAddr() );
            user1.setLastVisit( new Date() );
            loginService.loginSuccess( user1 );
            request.getSession().setAttribute( "user", user1 );
            logger.debug( "登陆成功！" );
        }
        responseData.setCode( code );
        responseData.setSuccess( success );
        responseData.setMessage(message);
        return responseData;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(HttpServletRequest request,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("rcode") String rcode,
                              @RequestParam("tm") String tm) {
        ResponseData responseData = new ResponseData();
        String code="S";
        boolean success=true;
        String message=null;
        User user = loginService.findUserByUsername( username );
        if(user!=null){
            code="E";
            success=false;
            message= "用户名已存在!" ;
        }
        //获取参数
        String verifyCode=(String)request.getSession().getAttribute("code");
        //验证码
        if(!rcode.toLowerCase().equals(verifyCode.toLowerCase())){
            success=false;
            code="codeerror";
        }
        responseData.setCode( code );
        responseData.setSuccess( success );
        responseData.setMessage(message);
        return responseData;

    }
}
