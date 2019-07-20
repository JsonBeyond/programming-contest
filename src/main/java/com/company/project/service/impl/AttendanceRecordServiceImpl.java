package com.company.project.service.impl;

import com.company.project.dao.AttendanceRecordMapper;
import com.company.project.model.AttendanceRecord;
import com.company.project.service.AttendanceRecordService;
import com.company.project.core.AbstractService;
import com.company.project.service.AttendanceResultService;
import com.company.project.web.vo.AttendanceRecordVo;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
@Service
public class AttendanceRecordServiceImpl extends AbstractService<AttendanceRecord> implements AttendanceRecordService {
    @Resource
    private AttendanceRecordMapper attendanceRecordMapper;
    @Resource
    private AttendanceResultService attendanceResultService;

    @Override
    public void batchInsert(List<AttendanceRecord> attendanceRecords) {
        attendanceRecordMapper.insertList(attendanceRecords);
    }

    @Override
    public List<AttendanceRecord> selectListByParams(AttendanceRecordVo attendanceRecordVo) {
        return attendanceRecordMapper.selectListByParams(attendanceRecordVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExcelRecord(List<AttendanceRecord> dataList) {
        //保存数据库
        List<List<AttendanceRecord>> partition = Lists.partition(dataList, 10000);
        for (List p: partition){
            this.batchInsert(p);
        }
        this.generateDataFromRecordToResult();
    }

    @Override
    public void generateDataFromRecordToResult() {
        long start = System.currentTimeMillis();
        attendanceRecordMapper.selectInto();
        attendanceResultService.updateFromRecord();
        System.out.println(System.currentTimeMillis()-start);
        attendanceResultService.doTime();
        System.out.println(System.currentTimeMillis()-start);
    }
}
