package com.company.project.service;
import com.alibaba.fastjson.JSONArray;
import com.company.project.model.AttendanceResult;
import com.company.project.core.Service;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
public interface AttendanceResultService extends Service<AttendanceResult> {

    void doTime();

    JSONArray rank(String dateLimit, Integer year, Integer range);
}
