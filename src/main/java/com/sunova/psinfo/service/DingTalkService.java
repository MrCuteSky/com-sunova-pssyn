package com.sunova.psinfo.service;

import com.alibaba.fastjson.JSONArray;
import com.sunova.psinfo.entities.Employee_Dt;

import java.util.List;

public interface DingTalkService {
    //获取access_key并保存到数据库中
    public int update_access_key(String Appkey,String Appsecret,String Application);

    //从数据库中获取access_key
    public String get_access(String Application);

    //获取全部部门
    public List<String> get_dept(String access_token);

    //获取所有部门的人员信息
    public JSONArray get_dept_per_all(String access_token);

    //获取单个部门的人员信息
    public JSONArray get_dept_per(Long dept_id,String access_token);

    //查看当前权限
    public String get_field(String access_token);

    //获得所有人员粗信息（不包含工号）
    public List<Employee_Dt> get_all_per_detail(String access_token);

    //获取单人详细信息
    public Employee_Dt get_simgle_per(String userid, String access_token);
}
