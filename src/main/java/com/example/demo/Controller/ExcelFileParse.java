package com.example.demo.Controller;

import com.example.demo.service.ExcelFileParseService;
import com.example.demo.util.RestHttpReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ExcelFileParse {
    @Autowired
    private ExcelFileParseService excelFileParseService;
    @RequestMapping("/importFile")
    public RestHttpReply importFile(HttpServletRequest request, @RequestParam("synLoanVo") MultipartFile mfile) throws Exception {
        RestHttpReply reply = new RestHttpReply();
        excelFileParseService.fileImport(request,mfile,reply);
        return reply;
    }
}
