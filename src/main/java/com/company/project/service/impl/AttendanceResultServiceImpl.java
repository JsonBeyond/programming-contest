package com.company.project.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.dao.AttendanceResultMapper;
import com.company.project.model.AttendanceResult;
import com.company.project.service.AttendanceResultService;
import com.company.project.core.AbstractService;
import com.company.project.util.RedisUtils;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Set;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
@Service
public class AttendanceResultServiceImpl extends AbstractService<AttendanceResult> implements AttendanceResultService {
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

}
