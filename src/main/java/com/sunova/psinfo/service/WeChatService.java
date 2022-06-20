package com.sunova.psinfo.service;

import com.alibaba.fastjson.JSONArray;
import com.sunova.psinfo.entities.Employee_Wc;

import java.util.List;

public interface WeChatService {
    //获取access_key并保存到数据库中
    public int update_access_key();

    //从数据库中获取access_key
    public String get_access_token();

    public JSONArray get_department(String access_token);

    public List<Employee_Wc> get_per_detail(String access_token);
}
