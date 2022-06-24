package com.sunova.psinfo.mapper;

import com.sunova.psinfo.entities.PositionRel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PositionRelShrMapper {
    //清空表
    public int cleanTable();
    //插入
    public int insertTable(PositionRel positionRel);
}
