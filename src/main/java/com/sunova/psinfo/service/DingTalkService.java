package com.sunova.psinfo.service;

import com.alibaba.fastjson.JSONArray;
import com.sunova.psinfo.entities.Dept_Dt;
import com.sunova.psinfo.entities.Employee_Dt;

import java.util.List;

public interface DingTalkService {
    //获取access_key并保存到数据库中
    public int update_access_key(String Appkey,String Appsecret,String Application);

    //从数据库中获取access_key
    public String get_access(String Application);

    //获取全部部门
    public List<String> get_dept(String access_token);

    //获取全部部门详细信息
    public List<Dept_Dt> get_dept_all_detail(String access_token);

    //获取部门详细信息
    public Dept_Dt get_dept_detail(String dept_id,String access_token);

    //获取所有部门的人员信息
    public JSONArray get_dept_per_all(String access_token);

    //获取单个部门的人员信息
    public JSONArray get_dept_per(Long dept_id,String access_token);

    //获得所有人员详细信息
    public List<Employee_Dt> get_all_per_detail(String access_token);

    //获取单人详细信息
    public Employee_Dt get_simgle_per(String userid, String access_token);

    //清空并更新数据库(人员)
    public void init_Database_Employee_Dt(String access_token);

    //清空并更新数据库(部门)
    public void init_Database_Dept_Dt(String access_token);
}
