package com.sunova.psinfo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunova.psinfo.conponment.HttpCon;
import com.sunova.psinfo.entities.Authority;
import com.sunova.psinfo.entities.Dept_Wc;
import com.sunova.psinfo.entities.Employee_Wc;
import com.sunova.psinfo.mapper.AuthorityMapper;
import com.sunova.psinfo.mapper.DeptWcMapper;
import com.sunova.psinfo.mapper.EmployeeWcMapper;
import com.sunova.psinfo.service.WeChatService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WeChatServiceImpl implements WeChatService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    HttpCon httpCon;
    @Autowired
    AuthorityMapper authorityMapper;
    @Autowired
    EmployeeWcMapper employeeWcMapper;
    @Autowired
    DeptWcMapper deptWcMapper;

    @Override
    public String get_access_token() {
        Authority authority = authorityMapper.getAuthorityByName("Contacts_wc");
        return authority.getAccesskey();
    }

    @Override
    public int update_access_key() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        Map<String, String> params = new HashMap<>();
        params.put("corpid", "ww7b8bd64ebaafbcce");
        params.put("corpsecret", "LxoxqdvvlCIqsNBYPCz7yj_9TM380JEvUV9kfYehAkc");
        String result = "";
        try {
            result = httpCon.http_get(url, params);
        } catch (Exception e) {
            logger.error("*****更新企业微信通讯录token失败*****", e);
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
        Map<String, String> params = new HashMap<>();
        params.put("access_token", access_token);
        String result = "";
        logger.info("*****开始获取企业微信部门信息*****");
        try {
            result = httpCon.http_get(url, params);
        } catch (Exception e) {
            logger.error("*****获取企业微信部门信息错误*****", e);
        }
//        System.out.println("***************获取部门");
        logger.info("*****获取企业微信部门信息成功*****");
        return JSONArray.parseArray(JSONObject.parseObject(result).get("department").toString());
    }

    @Override
    public List<Employee_Wc> get_per_detail(String access_token) {
        List<Employee_Wc> result = new ArrayList<>();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list";
        JSONArray depts = get_department(access_token);
        logger.info("*****开始获取企业微信员工信息*****");
        try {
            for (int i = 0; i < depts.size(); i++) {
                JSONObject dept = depts.getJSONObject(i);
                Map<String, String> params = new HashMap<>();
                params.put("access_token", access_token);
                params.put("department_id", dept.getString("id"));
                String jsonStr = JSONObject.parseObject(httpCon.http_get(url, params)).get("userlist").toString();
                result.addAll(JSONArray.parseArray(jsonStr, Employee_Wc.class));
            }
        } catch (Exception e) {
            logger.error("获取企业微信员工信息失败!", e);
        }
        logger.info("获取企业微信员工信息成功!");
        return result;
    }

    @Override
    public void init_Database_Employee_Wc(String access_token) {
        //清空数据
        int i = employeeWcMapper.cleanTable();
        logger.info("*****清空employee_wc,共" + i + "条数据*****");
        //插入新数据
        List<Employee_Wc> list = get_per_detail(access_token);
        logger.info("*****开始更新Wc人员信息数据库*****");
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        EmployeeWcMapper employeeWcMapperNew = sqlSession.getMapper(EmployeeWcMapper.class);
        list.stream().forEach(employeeWcMapperNew::insertTable);
        sqlSession.commit();
        sqlSession.clearCache();
        logger.info("*****更新Wc人员信息数据库成功*****");
    }

    @Override
    public void init_Database_Dept_Wc(String access_token) {
        //清空数据
        int i = deptWcMapper.cleanTable();
        logger.info("*****清空dept_wc,共" + i + "条数据*****");
        //插入新数据
        List<Dept_Wc> list = JSONArray.parseArray(get_department(access_token).toString(), Dept_Wc.class);
        logger.info("*****开始更新Wc部门信息数据库*****");
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        DeptWcMapper deptWcMapperNew = sqlSession.getMapper(DeptWcMapper.class);
        list.stream().forEach(deptWcMapperNew::insertTable);
        sqlSession.commit();
        sqlSession.clearCache();
        logger.info("*****更新Wc部门信息数据库成功*****");
    }

}
