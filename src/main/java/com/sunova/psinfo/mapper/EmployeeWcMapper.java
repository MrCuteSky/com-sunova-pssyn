package com.sunova.psinfo.mapper;

import com.sunova.psinfo.entities.Employee_Wc;
import com.sunova.psinfo.entities.updateEntities.WeChatUp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeWcMapper {
    //清空表
    public int truncateTable();

    //清空表
    public int cleanTable();

    //插入
    public int insertTable(Employee_Wc employee_wc);

    //获取需要新增的人员信息
    public List<WeChatUp> getNewInsertInfo();
}

