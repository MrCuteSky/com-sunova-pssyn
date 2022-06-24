package com.sunova.psinfo.service;

import com.alibaba.fastjson.JSONArray;

public interface ShrService {
    //获得所有人员信息
    public JSONArray get_all_per_detail(String fileName) throws Exception;

    //获取部门信息
    public JSONArray get_dept(String fileName) throws Exception;

    //清空并更新数据库(人员)
    public void init_Database_Employee_Shr(String fileName) throws Exception;

    //清空并更新数据库(部门)
    public void init_Database_Dept_Shr(String fileName) throws Exception;

    //清空并更新数据库(职务)
    public void init_Database_Position_Shr(String fileName) throws Exception;

    //清空并更新数据库(人员职务关系)
    public void init_Database_PositionRel_Shr(String fileName) throws Exception;
}
