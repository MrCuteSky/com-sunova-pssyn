package com.sunova.psinfo.mapper;

import com.sunova.psinfo.entities.Position_Shr;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PositionShrMapper {
    //清空表
    public int cleanTable();
    //插入
    public int insertTable(Position_Shr position_shr);
}
