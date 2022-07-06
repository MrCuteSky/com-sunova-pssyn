package com.sunova.psinfo.controller;

import com.sunova.psinfo.conponment.AdCon;
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
    @Autowired
    AdCon adCon;

    //通过authCode获取userId
    @PostMapping("/dingtalk/oa/get/userid")
    public Map<String, Object> getUseridByCode(@RequestBody Map params) {
        Map<String, Object> result = new HashMap<>();
        Object o = params.get("authCode");
        if (o == null) {
            result.put("code", 300);
            result.put("message", "认证代码获取失败！");
            return result;
        }
        logger.info("获取到的authCode：{}", o.toString());
        String access_token = dingTalkService.get_access(Context.DD_AD);
        String job_number = adService.getUserByAuthcode(o.toString(), access_token);
        if (job_number == null || job_number.equals("")) {
            result.put("code", 302);
            result.put("message", "未能成功获取到当前用户工号！");
            return result;
        }
        result.put("code", 200);
        result.put("message", "账号信息获取成功！");
        result.put("job_number", job_number);
        return result;
    }

    //重置密码
    @PostMapping("/dingtalk/oa/modify/password")
    public Map<String, Object> setPassword(@RequestBody Map params) {
        Map<String, Object> result = new HashMap<>();
        Object temp1 = params.get("jobnumber");
        Object temp2 = params.get("password");
        if (temp1 == null || temp2 == null) {
            logger.error("******获取的数据有空，请求错误！******");
            result.put("code", 305);
            result.put("message", "获取的数据有空，请求错误！");
            return result;
        }
        logger.info("*****域控密码修改入参，账号:"+temp1.toString()+"|密码:"+temp2.toString()+"******");
        try {
            adCon.modifyPassWord(temp1.toString(),temp2.toString());
            result.put("code", 200);
            result.put("message","密码修改成功!");
        } catch (Exception e) {
            if(e.getMessage().contains("5003")) {
                logger.error("*****密码违法策略要求*****",e);
                result.put("code", 303);
                result.put("message","密码需要同时包含大小写字母，数字，长度至少8位!");
            }else{
                logger.error("*****用户密码重置错误*****",e);
                result.put("code", 304);
                result.put("message","密码重置错误!");
            }
        }
        return result;
    }

}
