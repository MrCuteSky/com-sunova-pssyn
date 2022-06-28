package com.sunova.psinfo.controller;

import com.sunova.psinfo.service.AdService;
import com.sunova.psinfo.service.DingTalkService;
import com.sunova.psinfo.util.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AdService adService;
    @Autowired
    DingTalkService dingTalkService;
    //通过authCode获取userId
    @PostMapping("/dingtalk/oa/get/userid")
    public Map<String,Object> getUseridByCode(@RequestBody Map params){
        Map<String,Object> result = new HashMap<>();
        Object o = params.get("authCode");
        if(o==null){
            result.put("code",300);
            result.put("message","认证代码获取失败！");
            return result;
        }
        logger.info("获取到的authCode：{}",o.toString());
        String access_token = dingTalkService.get_access(Context.DD_AD);
        String job_number = adService.getUserByAuthcode(o.toString(),access_token);
        result.put("code",200);
        result.put("message","账号信息获取成功！");
        result.put("job_number",job_number);
        return result;
    }

    //重置密码
    @PostMapping("/dingtalk/oa/modify/password")
    public Map<String,Object> setPassword(@RequestBody Map params){
        Map<String,Object> result = new HashMap<>();


        return result;
    }
}
