package com.jiao.xy99.user.service.lmpl;
import com.jiao.xy99.system.util.ResponseData;
import com.jiao.xy99.user.controllers.LoginController;
import com.jiao.xy99.user.dto.User;
import com.jiao.xy99.user.dto.UserLoginLog;
import com.jiao.xy99.user.mapper.UserMapper;
import com.jiao.xy99.user.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by xieshuai on 2018/4/8.
 */
@Service
public class LoginServicelmpl implements ILoginService {

    @Autowired(required=false)
    private UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByName(username);
    }

    /**
     * 登录成功后 更新登录信息
     * 加方法的事务回滚
     * @param user
     * @param loginLog
     */
    @Transactional(rollbackFor = Exception.class)
    public void loginSuccess(User user,UserLoginLog loginLog) {
        userMapper.updateLoginInfo(user);
        userMapper.insertLoginLog(loginLog);
    }

    @Override
    public boolean validateSessionLogin(HttpServletRequest request) {
        Boolean status =false;
        String username= (String) request.getSession().getAttribute("username");
        String password= (String) request.getSession().getAttribute("password");
        int isVaildUser = userMapper.getMatchCount(username, password);
        if (isVaildUser>0) {
            status =true;
        }
        return status;
    }

    @Override
    public ResponseData login(HttpServletRequest request,
                              String username,
                              String password,
                              String myVerifyCode,
                              String time) {
        ResponseData responseData = new ResponseData();
        String code="S";
        boolean success=true;
        String message=null;
        //获取参数
        String verifyCode=(String)request.getSession().getAttribute("code");
        //验证码
//        if(!myVerifyCode.toLowerCase().equals(verifyCode.toLowerCase())){
//            success=false;
//            code="codeerror";
//        }
        int isVaildUser = userMapper.getMatchCount(username, password);

        logger.debug( "开始登录。。。"+username );
        if (isVaildUser==0) {
            code="E";
            success=false;
            message= "用户名或密码错误!" ;
        } else {
            User user = this.findUserByUsername( username );
            //登录次数
            user.setLoginNum(user.getLoginNum() +1);
            //最后登录IP
            user.setLastIp(request.getLocalAddr());
            UserLoginLog loginLog =new UserLoginLog();
            loginLog.setUserId(user.getUserId());
            loginLog.setIp(request.getLocalAddr());
            loginLog.setLoginTime(new Date());
            this.loginSuccess( user ,loginLog);
            request.getSession().setAttribute( "user", user );
            logger.debug( "登陆成功！" );
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("password", password);
        }
        responseData.setCode( code );
        responseData.setSuccess( success );
        responseData.setMessage(message);
        return responseData;
    }

    @Override
    public ResponseData register(HttpServletRequest request, String username, String password, String name, String email, String rcode, String tm) {
        ResponseData responseData = new ResponseData();
        String code="S";
        boolean success=true;
        String message=null;
        User user = this.findUserByUsername( username );
        if(user!=null){
            code="E";
            success=false;
            message= "用户名已存在!" ;
        }
        //获取参数
        String verifyCode=(String)request.getSession().getAttribute("code");
        //验证码
//        if(!rcode.toLowerCase().equals(verifyCode.toLowerCase())){
//            success=false;
//            code="codeerror";
//        }
        responseData.setCode( code );
        responseData.setSuccess( success );
        responseData.setMessage(message);
        return responseData;
    }

}
