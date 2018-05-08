package com.jiao.xy99.user.mapper;

import com.jiao.xy99.user.dto.Function;
import com.jiao.xy99.user.dto.Resource;
import com.jiao.xy99.user.dto.User;
import com.jiao.xy99.user.dto.UserLoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xieshuai on 2018/1/19.
 */
public interface UserMapper {

    public void insertLoginLog(UserLoginLog loginLog);

    public int getMatchCount(@Param("name") String name,@Param("password") String password);

    public User findUserByName(@Param("name") String name);

    public void updateLoginInfo(User user);

    public List<Map<String,String>> getFuntionMap();

    Resource selectByPrimaryKey(@Param("resourceId") Long resourceId);

    List<Function> selectFunctionAll();
}
