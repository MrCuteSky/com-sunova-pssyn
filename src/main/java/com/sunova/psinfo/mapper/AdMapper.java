package com.sunova.psinfo.mapper;

import com.sunova.psinfo.entities.AdLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdMapper {
    int insertLog(AdLog adLog);
}
