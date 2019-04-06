package com.jiao.xy99.user.controllers;

import com.jiao.xy99.api.test.JxlsDataRenderUtil;
import com.jiao.xy99.system.util.ResponseData;
import com.jiao.xy99.user.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData login(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> dataMap=new HashMap<>();
        //渲染单个值
        dataMap.put("data","hahah");
        //渲染list
        List<String> list=new ArrayList<>();
        list.add("a");
        list.add("b");
        dataMap.put("list",list);
        //渲染DTO
        List<User> userList=new ArrayList<>();
        User user=new User();
        user.setEmail("aEmail");
        user.setEmail("bEmail");
        userList.add(user);
        dataMap.put("user",userList);
        new JxlsDataRenderUtil().excelDataRender(response,  dataMap,"/templateExcel/excelabc.xlsx","newexcel.xlsx");
        return null;
    }

}
