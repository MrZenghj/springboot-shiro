package com.example.demo.exception;

public class TokenValidException extends AppException{

    private static final long serialVersionUID = 1L;

    private static final String HttpReplyCode = "S003";
    public TokenValidException(String token){
        super(HttpReplyCode,"Invalid t0ken <" + ">," + "验证失败，请重新登录。");
    }
    public TokenValidException(String token, String tooltips) {
        super(HttpReplyCode,token, tooltips);
    }
}
