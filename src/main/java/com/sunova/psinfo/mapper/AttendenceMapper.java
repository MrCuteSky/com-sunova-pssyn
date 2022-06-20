package com.sunova.psinfo.mapper;

import com.sunova.psinfo.entities.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AttendenceMapper {
    public int insertAttendenceInfo(Attendance attendance);
}
