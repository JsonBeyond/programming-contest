package com.company.project.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.dao.AttendanceResultMapper;
import com.company.project.model.AttendanceResult;
import com.company.project.service.AttendanceResultService;
import com.company.project.core.AbstractService;
import com.github.pagehelper.PageHelper;
import com.pagoda.common.utils.DateUtils;
import com.company.project.util.RedisUtils;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Calendar;
import java.util.List;
import java.util.Set;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
@Service
public class AttendanceResultServiceImpl extends AbstractService<AttendanceResult> implements AttendanceResultService {
    private static final int EVERY_HANDLE_DATA_SIZE = 1000000;
    @Resource
    private AttendanceResultMapper attendanceResultMapper;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public void doTime() {
        Condition condition = new Condition(AttendanceResult.class);
        condition.createCriteria().andEqualTo("status", "Y");
        List<AttendanceResult> list = attendanceResultMapper.selectByCondition(condition);
        if (list == null) {
            return;
        }
        for (AttendanceResult result : list) {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(result.getAttendance_start_time());
            String startKey = startCalendar.get(Calendar.YEAR) + ":" + startCalendar.get(Calendar.MONTH) + 1
                    + ":" + startCalendar.get(Calendar.WEEK_OF_YEAR)
                    + ":" + startCalendar.get(Calendar.DAY_OF_MONTH);
            int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
            int startMinute = startCalendar.get(Calendar.MINUTE);
            int score = (startHour - 6) * 60 + startMinute;

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(result.getAttendance_end_time());
            int endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
            int endMinute = endCalendar.get(Calendar.MINUTE);
            int endScore = (24 - endHour) * 60 + endMinute;

            redisUtils.putZSet(startKey, result.getStaff_name() + "," + result.getDep_code(), score + endScore);

        }

    }

    @Override
    public JSONArray rank(String dateLimit, Integer year, Integer range) {
        JSONArray ranks = new JSONArray();
        if (dateLimit.equalsIgnoreCase("W")) {
            //周
            String keys = year + ":*:"+range+":*";
            Set<String> keySet = redisUtils.keys(keys);
            if (keySet == null || keySet.size() <= 0) {
                return null;
            }
            doDestKey(year, ranks, keySet);
        } else if (dateLimit.equalsIgnoreCase("M")) {
            //月
            String srange = range.toString();
            if (range < 10) {
                srange = "0"+range;
            }
            String keys = year + ":" + srange + ":*";
            Set<String> keySet = redisUtils.keys(keys);
            if (keySet == null || keySet.size() <= 0) {
                return null;
            }
            doDestKey(year, ranks, keySet);
        } else if (dateLimit.equalsIgnoreCase("Y")) {
            //年
            String keys = year + ":*";
            Set<String> keySet = redisUtils.keys(keys);
            if (keySet == null || keySet.size() <= 0) {
                return null;
            }
            doDestKey(year, ranks, keySet);
        }
        return ranks;
    }

    private void doDestKey(Integer year, JSONArray ranks, Set<String> keySet) {
        String destKey = "dest:" + year + ":rank";
        for (String key : keySet) {
            Set<ZSetOperations.TypedTuple<String>> zSetReverse = redisUtils.getZSetReverse(key, 0, 9);
            for (ZSetOperations.TypedTuple<String> tuple : zSetReverse) {
                redisUtils.incrementZSetScore(destKey, tuple.getValue(), tuple.getScore());
            }
        }
        Set<ZSetOperations.TypedTuple<String>> zSetReverse = redisUtils.getZSetReverse(destKey, 0, 9);

        int rank = 1;
        for (ZSetOperations.TypedTuple<String> tuple : zSetReverse) {
            System.out.println(tuple.getValue() + " \t" + tuple.getScore());
            String[] svalue = tuple.getValue().split(",");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", svalue[0]);
            jsonObject.put("department", svalue[1]);
            jsonObject.put("ranking", rank);
            ranks.add(jsonObject);
            rank++;
        }
        redisUtils.delete(destKey);
    }

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
