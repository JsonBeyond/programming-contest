package com.company.project.service.impl;

import com.company.project.dao.AttendanceRecordMapper;
import com.company.project.model.AttendanceRecord;
import com.company.project.service.AttendanceRecordService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
@Service
@Transactional
public class AttendanceRecordServiceImpl extends AbstractService<AttendanceRecord> implements AttendanceRecordService {
    @Resource
    private AttendanceRecordMapper attendanceRecordMapper;

}
