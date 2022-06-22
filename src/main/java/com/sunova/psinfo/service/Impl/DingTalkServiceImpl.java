package com.sunova.psinfo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.sunova.psinfo.entities.Authority;
import com.sunova.psinfo.entities.Employee_Dt;
import com.sunova.psinfo.mapper.AuthorityMapper;
import com.sunova.psinfo.service.DingTalkService;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

@Service
public class DingTalkServiceImpl implements DingTalkService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AuthorityMapper authorityMapper;

    @Override
    public int update_access_key(String Appkey,String Appsecret,String Application) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(Appkey);
        request.setAppsecret(Appsecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = null;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            logger.error("*****更新钉钉token失败*****",e);
        }
        Authority authority = new Authority();
        authority.setAccesskey(JSONObject.parseObject(response.getBody()).getString("access_token"));
        authority.setApplication(Application);
        return authorityMapper.updateAuthority(authority);
    }

    //从数据库获得access_key
    public String get_access(String Application){
        Authority authority = authorityMapper.getAuthorityByName(Application);
        return authority.getAccesskey();
    }


    //获取全部部门
    public List<String> get_dept(String access_token) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsubid");
        List<String> dept_list = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        stack.push("1");
        //深度搜索
        while(!stack.empty()){
            //当前查询部门ID
            String now_dept_id = stack.pop();
            //记录dept_id
            dept_list.add(now_dept_id);
            OapiV2DepartmentListsubidRequest req = new OapiV2DepartmentListsubidRequest();
            //查询当前部门的子部门
            req.setDeptId(Long.parseLong(now_dept_id));
            OapiV2DepartmentListsubidResponse rsp = null;
            try {
                rsp = client.execute(req, access_token);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = JSONObject.parseObject(rsp.getBody());
            String temp = jsonObject.get("result")==null?"":jsonObject.get("result").toString();
            String temp_list = temp.substring(temp.indexOf("[")+1,temp.indexOf("]"));
            if(temp_list.equals(""))
                continue;
            String[] depts = temp_list.split(",");
            for(int i = 0;i < depts.length;i++){
                stack.push(depts[i]);
            }
        }
        return dept_list;
    }

    //获取所有部门的人员信息
    public JSONArray get_dept_per_all(String access_token){
        List<String> dept_list = get_dept(access_token);
        Iterator<String> iterator = dept_list.iterator();
        JSONArray jsonArray = new JSONArray();
        while (iterator.hasNext()) {
            String dept_id = iterator.next();
            jsonArray.addAll(get_dept_per(Long.parseLong(dept_id),access_token));
        }
        //超过总人数，有同一个人出现在不同部门（子部门）
//        System.out.println(jsonArray.size());
        return jsonArray;
    }

    //获取单个部门的人员信息
    public JSONArray get_dept_per(Long dept_id,String access_token){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
        OapiV2UserListRequest req = new OapiV2UserListRequest();
        req.setDeptId(dept_id);
        req.setContainAccessLimit(false);
        req.setSize(Long.parseLong("100"));
        req.setOrderField("modify_desc");
        req.setLanguage("zh_CN");
        req.setCursor(Long.parseLong("0"));
        OapiV2UserListResponse rsp = null;
        try {
            rsp = client.execute(req,access_token);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(rsp.getBody());
        JSONArray jsonArray = JSONArray.parseArray(JSONObject.parseObject(jsonObject.get("result").toString()).get("list").toString());
        return jsonArray;
    }

    //查看当前权限
    public String get_field(String access_token){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/auth/scopes");
        OapiAuthScopesRequest req = new OapiAuthScopesRequest();
        req.setHttpMethod("GET");
        OapiAuthScopesResponse rsp = null;
        try {
            rsp = client.execute(req, access_token);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp.getBody();
    }

    public List<Employee_Dt> get_all_per_detail(String access_token){
        List<Employee_Dt> result = new ArrayList<Employee_Dt>();
        //获得所有人员粗信息
        JSONArray jsonArray = get_dept_per_all(access_token);
        for(int i=0;i<jsonArray.size();i++){
            //逐个查询
            result.add(get_simgle_per(jsonArray.getJSONObject(i).getString("userid"),access_token));
        }
        return result;
    }

    //获取单人详细信息
    public Employee_Dt get_simgle_per(String userid, String access_token){
        Employee_Dt result = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setLanguage("zh_CN");
            req.setUserid(userid);
            OapiV2UserGetResponse rsp = client.execute(req, access_token);
            JSONObject jsonObject = JSONObject.parseObject(rsp.getBody());
            result = JSONObject.parseObject(jsonObject.get("result").toString(), Employee_Dt.class);
        } catch (ApiException e) {
            logger.error("获取用户信息失败,userid:"+userid,e);
        }
        return result;
    }
}
