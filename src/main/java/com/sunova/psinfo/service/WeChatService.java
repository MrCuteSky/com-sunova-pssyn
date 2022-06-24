package com.sunova.psinfo.service;

import com.alibaba.fastjson.JSONArray;
import com.sunova.psinfo.entities.Employee_Wc;

import java.util.List;

public interface WeChatService {
    //获取access_key并保存到数据库中
    public int update_access_key();

    //从数据库中获取access_key
    public String get_access_token();

    //获取部门信息
    public JSONArray get_department(String access_token);

    //获取人员信息
    public List<Employee_Wc> get_per_detail(String access_token);

    //清空并更新数据库(人员)
    public void init_Database_Employee_Wc(String access_token);

    //清空并更新数据库(部门)
    public void init_Database_Dept_Wc(String access_token);
}
