package com.example.demo.Controller;

import com.example.demo.util.RestHttpReply;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {
    @RequestMapping("/logging")
    public RestHttpReply String(){
        RestHttpReply reply = new RestHttpReply();
        reply.putData("hello","hello");
        return reply;
    }
}
