package com.jiao.xy99.user.service;

import com.jiao.xy99.system.util.ResponseData;
import com.jiao.xy99.user.dto.MenuItem;
import com.jiao.xy99.user.dto.Resource;
import com.jiao.xy99.user.dto.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    /**
     * 查询当前登录用户角色合并后的菜单.
     *
     *
     * @param request IRequest
     * @return 当前登录用户角色合并后的菜单集合
     */
    List<MenuItem> selectRoleFunctions(HttpServletRequest request);

    /**
     * 查询角色的所有功能ID的集合.
     * @param roleId 角色Id
     * @return 角色功能Id集合
     */
//    Long[] getRoleFunctionById(Long roleId);
    /**
     * 根据资源的Id查询资源数据.
     *
     * @param id 资源ID
     * @return 资源
     */
    Resource selectResourceById(Long id);
}