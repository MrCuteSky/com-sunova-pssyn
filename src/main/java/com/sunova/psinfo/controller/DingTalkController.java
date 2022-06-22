package com.sunova.psinfo.controller;

import com.sunova.psinfo.conponment.ExcelCon;
import com.sunova.psinfo.conponment.FileDownCon;
import com.sunova.psinfo.entities.Employee_Dt;
import com.sunova.psinfo.service.DingTalkService;
import com.sunova.psinfo.util.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String access_token = dingTalkService.get_access(Context.DD_Contacts);
            List<Employee_Dt> list = dingTalkService.get_all_per_detail(access_token);
            String fileName = excelCon.GenExcel(list,"person_dd");
//            fileDownCon.downloadFile(request,response,fileName);   //文件下载
//            return new CommonResult(200,"员工信息获取成功").toString();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("*****员工信息获取失败:" + e.getMessage() + "*****",e);
//            return new CommonResult(402,e.getMessage()).toString();
        }
    }


    //通过authCode获取userId
    @PostMapping("/dingtalk/oa/modify/password")
    public Map<String,Object> get_userIdbycode(@RequestBody Map params){
        Map<String,Object> result = new HashMap<>();
        Object o = params.get("authCode");
        if(o==null){
            result.put("code",300);
            result.put("message","认证代码不可为空！");
            return result;
        }
        logger.info("获取到的authCode：");
        return null;
    }
}
