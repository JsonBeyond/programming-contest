package com.company.project.service;
import com.company.project.model.AttendanceRecord;
import com.company.project.core.Service;
import com.company.project.web.vo.AttendanceRecordVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
public interface AttendanceRecordService extends Service<AttendanceRecord> {

    void batchInsert(List<AttendanceRecord> attendanceRecords);

    List<AttendanceRecord> selectListByParams(AttendanceRecordVo attendanceRecordVo);

    void saveExcelRecord(List<AttendanceRecord> dataList);

    void generateDataFromRecordToResult();
}
