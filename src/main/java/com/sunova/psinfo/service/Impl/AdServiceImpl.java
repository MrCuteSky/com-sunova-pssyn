package com.sunova.psinfo.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.sunova.psinfo.conponment.HttpCon;
import com.sunova.psinfo.entities.AdLog;
import com.sunova.psinfo.mapper.AdMapper;
import com.sunova.psinfo.service.AdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdServiceImpl implements AdService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    HttpCon httpCon;
    @Autowired
    AdMapper adMapper;
    @Override
    public String getUserByAuthcode(String authCode, String access_token) {
        String url = "https://oapi.dingtalk.com/user/getuserinfo";
        Map<String, String> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("code", authCode);
        String userid = null;
        try {
            JSONObject jsonObject = JSONObject.parseObject(httpCon.http_get(url, params));
            //获取工号
            String _url = "https://oapi.dingtalk.com/user/get";
            params.clear();
            params.put("access_token", access_token);
            params.put("userid",jsonObject.getString("userid"));
            JSONObject _jsonObject = JSONObject.parseObject(httpCon.http_get(_url, params));
            Object o = _jsonObject.get("jobnumber");
            if(o!=null){
                userid = o.toString();
            }
        } catch (Exception e) {
            logger.error("*****免登录获取用户信息失败!******", e);
        }
        return userid;
    }

    @Override
    public int insertLog(AdLog adLog) {
        return adMapper.insertLog(adLog);
    }
}
