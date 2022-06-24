package com.sunova.psinfo.mapper;

import com.sunova.psinfo.entities.Dept_Shr;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DeptShrMapper {
    //清空表
    public int truncateTable();

    //清空表
    public int cleanTable();

    //插入
    public int insertTable(Dept_Shr dept_shr);
}
