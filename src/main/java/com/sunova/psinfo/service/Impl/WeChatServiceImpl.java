package com.sunova.psinfo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunova.psinfo.conponment.HttpCon;
import com.sunova.psinfo.entities.Authority;
import com.sunova.psinfo.entities.Employee_Wc;
import com.sunova.psinfo.mapper.AuthorityMapper;
import com.sunova.psinfo.service.WeChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    HttpCon httpCon;

    @Autowired
    AuthorityMapper authorityMapper;

    @Override
    public String get_access_token() {
        Authority authority = authorityMapper.getAuthorityByName("Contacts_wc");
        return authority.getAccesskey();
    }

    @Override
    public int update_access_key() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        Map<String,String> params = new HashMap<>();
        params.put("corpid","ww7b8bd64ebaafbcce");
        params.put("corpsecret","LxoxqdvvlCIqsNBYPCz7yj_9TM380JEvUV9kfYehAkc");
        String result = "";
        try{
            result = httpCon.http_get(url,params);
        }catch (Exception e){
            logger.error("*****更新企业微信通讯录token失败*****",e);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        Authority authority = new Authority();
        authority.setAccesskey(jsonObject.get("access_token").toString());
        authority.setApplication("Contacts_wc");
        return authorityMapper.updateAuthority(authority);
    }

    @Override
    public JSONArray get_department(String access_token) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
        Map<String,String> params = new HashMap<>();
        params.put("access_token",access_token);
        String result = "";
        try{
            result = httpCon.http_get(url,params);
        }catch (Exception e){
            logger.error("*****获取微信部门信息错误*****",e);
        }
//        System.out.println("***************获取部门");
        logger.info("*****获取微信部门信息成功*****");
        return JSONArray.parseArray(JSONObject.parseObject(result).get("department").toString());
    }

    @Override
    public List<Employee_Wc> get_per_detail(String access_token) {
        List<Employee_Wc> result = new ArrayList<>();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list";
        JSONArray depts = get_department(access_token);
        try {
            for(int i=0;i<depts.size();i++){
                JSONObject dept = depts.getJSONObject(i);
                Map<String,String> params = new HashMap<>();
                params.put("access_token",access_token);
                params.put("department_id",dept.getString("id"));
                String jsonStr = JSONObject.parseObject(httpCon.http_get(url,params)).get("userlist").toString();
                result.addAll(JSONArray.parseArray(jsonStr, Employee_Wc.class));
            }
        }catch (Exception e){
            logger.error("获取企业微信员工信息失败!",e);
        }
        logger.info("获取企业微信员工信息成功!");
        return result;
    }

}
