package com.example.demo.Controller;

import com.example.demo.util.RestHttpReply;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NonName {
    @RequestMapping("/NonName")
    public RestHttpReply NonName(){
        RestHttpReply reply = new RestHttpReply();
        reply.putData("none","匿名登录");
        return reply;
    }
}
