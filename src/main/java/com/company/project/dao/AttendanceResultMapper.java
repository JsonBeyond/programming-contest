package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.AttendanceResult;

public interface AttendanceResultMapper extends Mapper<AttendanceResult> {
    void updateFromRecord();
}