package com.sunova.psinfo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.sunova.psinfo.conponment.JsonCon;
import com.sunova.psinfo.entities.Dept_Shr;
import com.sunova.psinfo.entities.Employee_Shr;
import com.sunova.psinfo.mapper.DeptShrMapper;
import com.sunova.psinfo.mapper.EmployeeShrMapper;
import com.sunova.psinfo.service.ShrService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShrServiceImpl implements ShrService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    JsonCon jsonCon;
    @Autowired
    EmployeeShrMapper employeeShrMapper;
    @Autowired
    DeptShrMapper deptShrMapper;

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


    @Override
    public void init_Database_Employee_Shr(String fileName) throws Exception{
        //清空数据库
        int i = employeeShrMapper.cleanTable();
        logger.info("*****清空employee_shr,共"+i+"条数据");
        //插入数据
        logger.info("*****开始更新Shr人员信息数据库*****");
        List<Employee_Shr> list = JSONArray.parseArray(get_all_per_detail(fileName).toString(), Employee_Shr.class);
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        EmployeeShrMapper employeeShrMapperNew = sqlSession.getMapper(EmployeeShrMapper.class);
        list.stream().forEach(employeeShrMapperNew::insertTable);
        sqlSession.commit();
        sqlSession.clearCache();
        logger.info("*****更新Shr人员信息数据库成功*****");
    }

    @Override
    public void init_Database_Dept_Shr(String fileName) throws Exception{
        //清空数据库
        int i = deptShrMapper.cleanTable();
        logger.info("*****清空dept_shr,共"+i+"条数据*****");
        //插入数据
        logger.info("*****开始更新Shr部门信息数据库*****");
        List<Dept_Shr> list = JSONArray.parseArray(get_dept(fileName).toString(), Dept_Shr.class);
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        DeptShrMapper deptShrMapperNew = sqlSession.getMapper(DeptShrMapper.class);
        list.stream().forEach(deptShrMapperNew::insertTable);
        sqlSession.commit();
        sqlSession.clearCache();
        logger.info("*****更新Shr部门信息数据库成功*****");
    }
}
