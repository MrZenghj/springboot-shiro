package com.example.demo.config;

import com.example.demo.util.RestHttpReply;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class ExceptionController {

    //捕捉shiro 的异常
    @ExceptionHandler({ShiroException.class})
    public RestHttpReply handleShiroException(Exception e){
        return new RestHttpReply(HttpStatus.FORBIDDEN,e.getMessage(),"您未获得进行当前操作的权限");
    }

    //捕捉Authentication 的异常
    @ExceptionHandler({JwtException.class})
    public RestHttpReply handleJWTException(Exception e){
        return new RestHttpReply(HttpStatus.UNAUTHORIZED,e.getMessage(),"您使用的是已失效或非法的token");
    }

    //捕捉Authentication 的异常
    @ExceptionHandler({ServletRequestBindingException.class})
    public RestHttpReply handleServletRequestBindingException(Exception e){
        return new RestHttpReply(HttpStatus.NOT_ACCEPTABLE,e.getMessage(),"请求缺少必要的参数");
    }

    //捕捉其他的异常
    @ExceptionHandler({Exception.class})
    public RestHttpReply globalException(Exception e){
        //输入日志

        return new RestHttpReply(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"出错了，请联系管理员");
    }

}
