package com.company.project.service.impl;

import com.company.project.dao.AttendanceRecordMapper;
import com.company.project.model.AttendanceRecord;
import com.company.project.service.AttendanceRecordService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
@Service
public class AttendanceRecordServiceImpl extends AbstractService<AttendanceRecord> implements AttendanceRecordService {
    @Resource
    private AttendanceRecordMapper attendanceRecordMapper;

    @Override
    public void batchInsert(List<AttendanceRecord> attendanceRecords) {
        attendanceRecordMapper.insertList(attendanceRecords);
    }
}
