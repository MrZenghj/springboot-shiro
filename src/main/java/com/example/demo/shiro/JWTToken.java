package com.example.demo.shiro;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 *  不需要RememberMe这类功能，我们简单的实现下AuthenticationToken接口即可
 * */
@Data
public class JWTToken implements AuthenticationToken {

    private static final long serialVersionUId = 1;

    // 密钥
    private String token;

    public JWTToken(String token) {
        super();
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}