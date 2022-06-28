package com.sunova.psinfo.service;

public interface AdService {
    //无认证登录(获取工号)
    public String getUserByAuthcode(String authCode, String access_token);
}
