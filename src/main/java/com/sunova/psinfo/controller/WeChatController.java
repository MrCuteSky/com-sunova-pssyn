package com.sunova.psinfo.controller;

import com.sunova.psinfo.conponment.ExcelCon;
import com.sunova.psinfo.conponment.FileDownCon;
import com.sunova.psinfo.entities.Employee_Wc;
import com.sunova.psinfo.service.WeChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class WeChatController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    WeChatService weChatService;
    @Autowired
    ExcelCon<Employee_Wc> excelCon;
    @Autowired
    FileDownCon fileDownCon;
    @GetMapping("/wechat/empolyee/get/info")
    public void empolyee_get_info(HttpServletRequest request, HttpServletResponse response){
        logger.info("*****开始读取企业微信员工信息并生成Excel*****");
        try{
            String access_token = weChatService.get_access_token();
            List<Employee_Wc> list = weChatService.get_per_detail(access_token);
            String fileName = excelCon.GenExcel(list,"person_wc");
//            fileDownCon.downloadFile(request,response,fileName); //文件下载
//            return new CommonResult(200,"员工信息获取成功").toString();
        }catch (Exception e){
//            e.printStackTrace();
            logger.error("*****员工信息获取失败:" + e.getMessage() + "*****",e);
//            return new CommonResult(402,e.getMessage()).toString();
        }
    }

    @GetMapping("/wechat/empolyee/init/database")
    public void updateEmpDb(){
        String access_token = weChatService.get_access_token();
        weChatService.init_Database_Employee_Wc(access_token);
    }

    @GetMapping("/wechat/dept/init/database")
    public void updateDepDb(){
        String access_token = weChatService.get_access_token();
        weChatService.init_Database_Dept_Wc(access_token);
    }
}
