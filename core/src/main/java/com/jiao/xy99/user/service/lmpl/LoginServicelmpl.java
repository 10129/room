package com.jiao.xy99.user.service.lmpl;
import com.jiao.xy99.system.util.ResponseData;
import com.jiao.xy99.user.controllers.LoginController;
import com.jiao.xy99.user.dto.*;
import com.jiao.xy99.user.mapper.UserMapper;
import com.jiao.xy99.user.service.ILoginService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
     * @param user
     * @param loginLog
     */
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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuItem> selectRoleFunctions(HttpServletRequest request) {
        List<Function> functions = userMapper.selectFunctionAll();
        Set<Long> allRoleFunctionIds = new HashSet<>();
//        Long[] roleIds = request.getAllRoleId();
        //取出登录用户下所有角色的功能ID集合 去重角色拥有的相同功能
//        for (Long roleId : roleIds) {
//            Long[] roleFunctionIds = roleFunctionService.getRoleFunctionById(roleId);
        Long[] roleFunctionIds = {10175L,10176L,10177L};
            if (ArrayUtils.isNotEmpty(roleFunctionIds)) {
                allRoleFunctionIds.addAll(Arrays.asList(roleFunctionIds));
            }
//        }
        Map<Long, Function> functionMap = new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(functions)) {
            for (Function f : functions) {
                functionMap.put(f.getFunctionId(), f);
            }
        }
        Map<Long, MenuItem> menuMap = new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(allRoleFunctionIds)) {
            for (Long functionId : allRoleFunctionIds) {
                createMenuRecursive(menuMap, functionMap, functionId);
            }
        }
        List<MenuItem> itemList = new ArrayList<>();
        menuMap.forEach((k, v) -> {
            if (v.getParent() == null) {
                itemList.add(v);
            }
            if (v.getChildren() != null) {
                Collections.sort(v.getChildren());
            }
        });
        Collections.sort(itemList);
        return itemList;
    }

    /**
     * 递归创建当前登录用户的功能菜单.
     *
     * @param menuMap     功能菜单Map
     * @param functionMap 所有功能Map
     * @param functionId  当前登录用户拥有的功能Id
     * @return 功能菜单
     */
    private MenuItem createMenuRecursive(Map<Long, MenuItem> menuMap, Map<Long, Function> functionMap, Long functionId) {
        MenuItem menuItem = menuMap.get(functionId);
        if (menuItem == null) {
            Function function = functionMap.get(functionId);
            if (function == null) {
                // role has a function that dose not exists.
                return null;
            }
            menuItem = createMenuItem(function);
            menuMap.put(functionId, menuItem);
            // create parent menuItem
            Long parentId = function.getParentFunctionId();
            if (parentId != null) {
                MenuItem parentMenuItem = createMenuRecursive(menuMap, functionMap, parentId);
                if (parentMenuItem != null) {
                    List<MenuItem> children = parentMenuItem.getChildren();
                    if (children == null) {
                        children = new ArrayList<>();
                        parentMenuItem.setChildren(children);
                    }
                    menuItem.setParent(parentMenuItem);
                    children.add(menuItem);
                }
            }
        }
        return menuItem;
    }
    /**
     * 创建功能菜单.
     *
     * @param function 功能
     * @return 功能菜单叶子节点
     */
    private MenuItem createMenuItem(Function function) {
        MenuItem menu = new MenuItem();
        menu.setText(function.getFunctionName());
        menu.setIcon(function.getFunctionIcon());
        menu.setFunctionCode(function.getFunctionCode());
        if (function.getResourceId() != null) {
            Resource resource = selectResourceById(function.getResourceId());
            if (resource != null) {
                menu.setUrl(resource.getUrl());
            }
        }
        menu.setId(function.getFunctionId());
        menu.setScore(function.getFunctionSequence());
        return menu;
    }

    @Override
    public Resource selectResourceById(Long id) {
            Resource resource = userMapper.selectByPrimaryKey(id);
        return resource;
    }
}
