package com.sunova.psinfo.conponment;


import com.sunova.psinfo.service.DingTalkService;
import com.sunova.psinfo.service.WeChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.sunova.psinfo.util.Context;

import javax.annotation.PostConstruct;

@Component
public class SpringTaskJob {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DingTalkService dingTalkService;
    @Autowired
    WeChatService weChatService;

    @PostConstruct
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void get_dd_access_key(){
        logger.info("******更新钉钉通讯录密钥定时任务开始执行******");
        int i = dingTalkService.update_access_key(Context.DD_Contacts_Appkey,Context.DD_Contacts_Appsecret,Context.DD_Contacts);
        if(i!=1){
            logger.error("更新钉钉通讯录密钥失败，联系管理员查明原因！");
        }else {
            logger.info("******更新钉钉通讯录密钥成功******");
        }
        int j = dingTalkService.update_access_key(Context.DD_AD_Appkey,Context.DD_AD_Appsecret,Context.DD_AD);
        if(j!=1){
            logger.error("更新钉钉域控密钥失败，联系管理员查明原因！");
        }else {
            logger.info("******更新钉钉域控密钥成功******");
        }
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void get_wc_access_key(){
        logger.info("******更新微信通讯录密钥定时任务开始执行******");
        int i = weChatService.update_access_key();
        if(i!=1){
            logger.error("更新微信通讯录密钥失败，联系管理员查明原因！");
        }else {
            logger.info("******更新微信通讯录密钥成功******");
        }
    }
}
