package com.example.demo.util;


import lombok.experimental.Accessors;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@JsonComponent
@Accessors
public class RestHttpReply {
    public static final String SUCCESS = "S000"; //成功
    public static final String RUNTIME_EXCEPTION = "S099"; // 运行时抛出异常
    public static final String UNKNOWN = "S100"; // 未知错误

    private String code = SUCCESS; //返回状态码
    private String desc; //返回错误消息
    private String tooltips; //页面提示消息
    private Map<String,Object> data; //返回数据体

    public void putData(String key,Object value){
        if(data == null){
            data = new HashMap<>();
        }
        data.put(key,value);
    }

    public RestHttpReply(HttpStatus status, String desc, String tooltips){
        this.code = "S" + status;
        this.desc = status.getReasonPhrase() + "-->" + desc;
        this.tooltips = tooltips;
    }
    public RestHttpReply(){
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTooltips() {
        return tooltips;
    }

    public void setTooltips(String tooltips) {
        this.tooltips = tooltips;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
