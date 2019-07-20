package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.AttendanceRecord;
import com.company.project.web.vo.AttendanceRecordVo;

import java.util.List;

public interface AttendanceRecordMapper extends Mapper<AttendanceRecord> {
    List<AttendanceRecord> selectListByParams(AttendanceRecordVo attendanceRecordVo);

    void selectInto();
}