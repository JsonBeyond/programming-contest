package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.AttendanceRecord;
import com.company.project.service.AttendanceRecordService;
import com.company.project.web.vo.AttendanceRecordVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pagoda.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/07/20.
 */
@RestController
@RequestMapping("/attendance/record")
@Slf4j
public class AttendanceRecordController {
    @Resource
    private AttendanceRecordService attendanceRecordService;

    @PostMapping("/add")
    public Result add(AttendanceRecord attendanceRecord) {
        attendanceRecordService.save(attendanceRecord);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        attendanceRecordService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(AttendanceRecord attendanceRecord) {
        attendanceRecordService.update(attendanceRecord);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        AttendanceRecord attendanceRecord = attendanceRecordService.findById(id);
        return ResultGenerator.genSuccessResult(attendanceRecord);
    }

    @PostMapping("/list")
    public Result list(AttendanceRecordVo attendanceRecordVo) {
        List<AttendanceRecord> list = attendanceRecordService.selectListByParams(attendanceRecordVo);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @PostMapping(value = "/upload")
    public Result uploadChannel(HttpServletRequest request) throws IOException {
        List<AttendanceRecord> dataList = new ArrayList<>();
        BufferedReader br = null;
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("filename");
            if (file.isEmpty()) {
                return ResultGenerator.genFailResult("文件不能为空");
            }
            InputStream inputStream = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream, "GB2312"));
            String line = "";
            br.readLine();
            int count = 0;
            while ((line = br.readLine()) != null) {
                try {


                    AttendanceRecord attendanceRecord = new AttendanceRecord();
                    String[] arrar = line.split(",");
                    attendanceRecord.setDep_code(arrar[0]);
                    attendanceRecord.setStaff_name(arrar[1]);
                    attendanceRecord.setAttendance_num(Integer.valueOf(arrar[2]));
                    attendanceRecord.setAttendance_time(DateUtils.parse(arrar[3], "yyyy/mm/dd HH:mm"));
                    attendanceRecord.setMachine_code(Integer.valueOf(arrar[4]));
                    if (count == 90295) {

                        System.out.println(count);
                    }
                    count++;
                    dataList.add(attendanceRecord);
                }catch (Exception e){

                }
            }
            attendanceRecordService.saveExcelRecord(dataList);
        } catch (Exception e) {
            log.error("上传异常", e);
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/listlimit")
    public Result listLimit(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<AttendanceRecord> list = attendanceRecordService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
