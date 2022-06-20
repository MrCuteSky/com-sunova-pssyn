package com.sunova.psinfo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.sunova.psinfo.conponment.JsonCon;
import com.sunova.psinfo.service.ShrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShrServiceImpl implements ShrService {
    @Autowired
    JsonCon jsonCon;

    @Override
    public JSONArray get_all_per_detail(String fileName) throws Exception{
        return jsonCon.readJsonFile(fileName,"org_number","230000",3);
    }

    @Override
    public JSONArray get_dept(String fileName) throws Exception {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(jsonCon.readJsonFile(fileName,"name","星诺",2));
        jsonArray.addAll(jsonCon.readJsonFile(fileName,"supFnumber","230000",1));
        return jsonArray;
    }
}
