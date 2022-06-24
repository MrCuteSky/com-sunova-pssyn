package com.sunova.psinfo.mapper;

import com.sunova.psinfo.entities.Dept_Dt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DeptDtMapper {
    //清空表(此方法不可回滚)
    public int truncateTable();

    //清空表
    public int cleanTable();

    //插入
    public int insertTable(Dept_Dt dept_dt);
}

