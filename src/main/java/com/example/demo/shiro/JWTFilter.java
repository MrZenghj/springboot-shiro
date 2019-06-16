package com.example.demo.shiro;

import com.example.demo.util.JsonUtils;
import com.example.demo.util.RestHttpReply;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *      过滤器
 * */
@WebFilter
public class JWTFilter extends AccessControlFilter {
    // 允许
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        //获取token 并对token转换
        HttpServletRequest httpRequest = WebUtils.toHttp(servletRequest);
        String authzHeader = httpRequest.getHeader("token2");
        System.out.println("authzHeader:==>" + authzHeader);
        if(authzHeader == null || authzHeader == ""){
            return false;
        }
        try{
            JWTToken jwtToken = new JWTToken(authzHeader);
            // 对shiro进行login token
            getSubject(servletRequest,servletResponse).login(jwtToken);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    // 拒绝后处理
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 前后端分离时，返回数据JSON（错误信息）
        HttpServletResponse response = WebUtils.toHttp(servletResponse);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        RestHttpReply reply = new RestHttpReply(HttpStatus.UNAUTHORIZED,"token invalidate!","您使用的是非法的token.");
        response.getWriter().write(JsonUtils.toJson(reply,new ObjectMapper()));
        response.getWriter().close();
        return false;
    }
}
