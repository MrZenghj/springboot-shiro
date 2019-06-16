package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

/**
 *  基本异常类
 */
public class AppException extends Exception{
    private static final long serialVersionUID = 1L;

    @Getter
    String httpReplyCode;

    @Setter
    String[] tooltips;

    public AppException(String httpReplyCode, String errorMsg, String... tooltips) {
        super(errorMsg);
        this.httpReplyCode = httpReplyCode;
        this.tooltips = tooltips;
    }
    public AppException(String httpReplyCode, String errorMsg,Exception e, String... tooltips) {
        super(errorMsg,e);
        this.httpReplyCode = httpReplyCode;
        this.tooltips = tooltips;
    }

    public String getTooltips(){
        StringBuilder sb = new StringBuilder();
        if(tooltips != null && tooltips.length > 0){
            for (String tip: tooltips) {
                sb.append(tip);
            }
        }
        return sb.toString();
    }
}
