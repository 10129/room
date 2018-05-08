package com.jiao.xy99.core.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shuai.xie on 2018/5/1.
 */
public class MonitorInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(MonitorInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        logger.info(basePath);
        request.setAttribute("basePath", basePath);
        StringBuffer aa=request.getRequestURL();
        String bb=request.getRequestURI();
        return true;
    }

}
