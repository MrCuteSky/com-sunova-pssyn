package com.sunova.psinfo.service;

import com.alibaba.fastjson.JSONArray;

public interface ShrService {
    //获得所有人员信息
    public JSONArray get_all_per_detail(String fileName) throws Exception;

    //获取部门信息
    public JSONArray get_dept(String fileName) throws Exception;
}
