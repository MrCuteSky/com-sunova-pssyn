package com.sunova.psinfo.service;

import com.sunova.psinfo.entities.AdLog;

public interface AdService {
    //无认证登录(获取工号)
    public String getUserByAuthcode(String authCode, String access_token);

    //写入日志
    public int insertLog(AdLog adLog);
}
