package com.company.project.service.impl;

import com.company.project.dao.AttendanceResultMapper;
import com.company.project.model.AttendanceResult;
import com.company.project.service.AttendanceResultService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
@Service
@Transactional
public class AttendanceResultServiceImpl extends AbstractService<AttendanceResult> implements AttendanceResultService {
    @Resource
    private AttendanceResultMapper attendanceResultMapper;

}
