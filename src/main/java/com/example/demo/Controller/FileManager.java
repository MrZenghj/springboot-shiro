package com.example.demo.Controller;
import java.io.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class FileManager {

    @RequestMapping
    public void fileDownload(HttpServletResponse response) throws IOException {
        File file = new File("");
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename="+new String( "中文".getBytes("utf-8"), "ISO8859-1" )+"文件类型");
        OutputStream out = response.getOutputStream();
         out.flush();
        response.flushBuffer();
    }
}
