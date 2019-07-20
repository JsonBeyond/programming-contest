package com.company.project.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.AttendanceRecord;
import com.company.project.model.AttendanceResult;
import com.company.project.service.AttendanceRecordService;
import com.company.project.service.AttendanceResultService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/07/20.
 */
@RestController
@RequestMapping("/attendance/result")
public class AttendanceResultController {
    @Resource
    private AttendanceResultService attendanceResultService;
    @Resource
    private AttendanceRecordService attendanceRecordService;

    @PostMapping("/add")
    public Result add(AttendanceResult attendanceResult) {
        attendanceResultService.save(attendanceResult);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        attendanceResultService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(AttendanceResult attendanceResult) {
        attendanceResultService.update(attendanceResult);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        AttendanceResult attendanceResult = attendanceResultService.findById(id);
        return ResultGenerator.genSuccessResult(attendanceResult);
    }

    @PostMapping("/list/{username}")
    public Result list(@PathVariable String username, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        Condition condition = new Condition(AttendanceResult.class);
        condition.createCriteria().andEqualTo("staff_name", username);
        List<AttendanceResult> list = attendanceResultService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/syncDataFromRecordToResult")
    public Result syncDataFromRecordToResult() {
        attendanceRecordService.generateDataFromRecordToResult();
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/rank")
    public Result list(@RequestBody JSONObject jsonObject) {
        String dateLimit = jsonObject.getString("dateLimit");
        Integer year = jsonObject.getInteger("year");
        Integer range = jsonObject.getInteger("range");
//        attendanceResultService.doTime();
        JSONArray rank = attendanceResultService.rank(dateLimit, year, range);
        return ResultGenerator.genSuccessResult(rank);
    }

}
