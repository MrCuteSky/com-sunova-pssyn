package com.sunova.psinfo.controller;

import com.sunova.psinfo.conponment.ExcelCon;
import com.sunova.psinfo.conponment.FileDownCon;
import com.sunova.psinfo.entities.Employee_Dt;
import com.sunova.psinfo.service.DingTalkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class DingTalkController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DingTalkService dingTalkService;
    @Autowired
    ExcelCon<Employee_Dt> excelCon;
    @Autowired
    FileDownCon fileDownCon;
    @GetMapping("/dingtalk/empolyee/get/info")
    public void empolyee_get_info(HttpServletRequest request, HttpServletResponse response){
        logger.info("*****开始读取钉钉员工信息并生成Excel*****");
        try{
            String access_token = dingTalkService.get_access();
            List<Employee_Dt> list = dingTalkService.get_all_per_detail(access_token);
            String fileName = excelCon.GenExcel(list,"dd");
//            fileDownCon.downloadFile(request,response,fileName);   //文件下载
//            return new CommonResult(200,"员工信息获取成功").toString();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("*****员工信息获取失败:" + e.getMessage() + "*****");
//            return new CommonResult(402,e.getMessage()).toString();
        }
    }

    @GetMapping("/test")
    public String test(){
        return "Success!";
    }
}
