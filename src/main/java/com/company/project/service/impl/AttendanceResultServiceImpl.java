package com.company.project.service.impl;

import com.company.project.dao.AttendanceResultMapper;
import com.company.project.model.AttendanceResult;
import com.company.project.service.AttendanceResultService;
import com.company.project.core.AbstractService;
import com.github.pagehelper.PageHelper;
import com.pagoda.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
@Service
@Transactional
public class AttendanceResultServiceImpl extends AbstractService<AttendanceResult> implements AttendanceResultService {
    private static final int EVERY_HANDLE_DATA_SIZE = 1000000;
    @Resource
    private AttendanceResultMapper attendanceResultMapper;

    @Override
    public void updateFromRecord() {
        attendanceResultMapper.updateFromRecord();
    }

//    List<AttendanceResult> getRanking() {
//        int totalCount = attendanceResultMapper.selectCount();
//        int batchNumber = totalCount / EVERY_HANDLE_DATA_SIZE + totalCount % EVERY_HANDLE_DATA_SIZE == 0 ? 0 : 1;
//        List<AttendanceResult> result = new ArrayList<>(10);
//        Map<String, Integer> map = new HashMap<>();
//        // 循环控制查询数据库的起始位置
//        for (int i = 0; i < batchNumber; i++) {
//            int page = EVERY_HANDLE_DATA_SIZE * i;
//            PageHelper.startPage(page, EVERY_HANDLE_DATA_SIZE);
//            List<AttendanceResult> attendanceResults = this.findAll();
//            for (int j = 0; j < attendanceResults.size(); j++) {
//                String staffName = attendanceResults.get(i).getStaff_name();
////                Integer period = DateUtils.diffMinute(attendanceResults.get(i).getAttendance_end_time(), attendanceResults.get(i).getAttendance_end_time());
//
//                if (map.containsKey(staffName)) {
//                    map.put(staffName, map.get(staffName) + 1);
//                } else {
//                    map.put(staffName, 1);
//                }
//            }
//
//        }
//        return result;
//    }
}
