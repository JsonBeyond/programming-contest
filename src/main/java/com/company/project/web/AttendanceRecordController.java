package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.AttendanceRecord;
import com.company.project.service.AttendanceRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<AttendanceRecord> list = attendanceRecordService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/upload-excel")
    public Result uploadExcel(HttpServletRequest request, HttpServletResponse response) {
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//            // 获取上传文件对象
//            MultipartFile file = entity.getValue();
//            ImportParams params = new ImportParams();
//            params.setTitleRows(2);
//            params.setHeadRows(1);
//            params.setNeedSave(true);
//            try {
//                List<SysUserAgent> listSysUserAgents = ExcelImportUtil.importExcel(file.getInputStream(), SysUserAgent.class, params);
//                for (SysUserAgent sysUserAgentExcel : listSysUserAgents) {
//                    sysUserAgentService.save(sysUserAgentExcel);
//                }
//                return Result.ok("文件导入成功！数据行数：" + listSysUserAgents.size());
//            } catch (Exception e) {
//                log.error(e.getMessage(),e);
//                return Result.error("文件导入失败！");
//            } finally {
//                try {
//                    file.getInputStream().close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return Result.error("文件导入失败！");
        return ResultGenerator.genSuccessResult();
    }
}
