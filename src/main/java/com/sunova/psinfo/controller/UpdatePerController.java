package com.sunova.psinfo.controller;

import com.sunova.psinfo.service.UpdatePerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatePerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UpdatePerService updatePerService;

    @GetMapping("/update/dingtalk")
    public void UpdateDingTalk(){
        updatePerService.UpdateDingTalk();
    }

    @GetMapping("/update/wechat")
    public void UpdateWeChat(){
        updatePerService.UpdateWeChat();
    }
}
