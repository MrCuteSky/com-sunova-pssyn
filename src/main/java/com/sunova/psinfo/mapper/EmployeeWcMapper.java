package com.sunova.psinfo.mapper;

import com.sunova.psinfo.entities.Employee_Wc;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EmployeeWcMapper {
    //清空表
    public int truncateTable();

    //清空表
    public int cleanTable();

    //插入
    public int insertTable(Employee_Wc employee_wc);
}
