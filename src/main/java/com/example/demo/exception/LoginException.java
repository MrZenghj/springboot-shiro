package com.example.demo.exception;

import com.example.demo.exception.AppException;

public class LoginException extends AppException {
    private static final long serialVersionUID = 1L;
    private static final String HttpReplyCode = "S002";
    public LoginException(String... tooltips) {
        super(HttpReplyCode, "Login failed", tooltips);
    }
}
