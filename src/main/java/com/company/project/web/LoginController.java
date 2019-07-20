package com.company.project.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.AttendanceRecord;
import com.company.project.model.BasEmp;
import com.company.project.service.AttendanceRecordService;
import com.company.project.service.BasEmpService;
import com.company.project.util.Constance;
import com.company.project.util.JwtUtils;
import com.company.project.util.RedisUtils;
import com.google.common.collect.Lists;
import com.pagoda.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author qxs on 2019/7/20.
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Resource
    private BasEmpService basEmpService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private AttendanceRecordService attendanceRecordService;

    @PostMapping("")
    @ResponseBody
    public Result login(@RequestBody JSONObject jsonObject) throws Exception {
        BasEmp user = basEmpService.login(jsonObject.getString("userName"));
        if (user == null) {
            return ResultGenerator.genFailResult("用户名或密码错误");
        }
        String token = JwtUtils.sign(user, 24L * 3600L * 1000L);
        redisUtils.setNx(Constance.LOGIN_KEY + token, "1", 1, TimeUnit.DAYS);
        return ResultGenerator.genSuccessResult(token);
    }

    @GetMapping("/{token}")
    @ResponseBody
    public Result token(@PathVariable String token) throws Exception {
        BasEmp unsign = JwtUtils.unsign(token, BasEmp.class);
        if (unsign == null) {
            return ResultGenerator.genFailResult("请登录");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", unsign.getName());
        jsonObject.put("access", new JSONArray().add("admin"));
        jsonObject.put("token", token);
        return ResultGenerator.genSuccessResult(token);
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
            while ((line = br.readLine()) != null) {
                AttendanceRecord attendanceRecord = new AttendanceRecord();
                String[] arrar = line.split(",");
                attendanceRecord.setDep_code(arrar[0]);
                attendanceRecord.setStaff_name(arrar[1]);
                attendanceRecord.setAttendance_num(Integer.valueOf(arrar[2]));
                attendanceRecord.setAttendance_time(DateUtils.parse(arrar[3], "yyyy/mm/dd HH:mm"));
                attendanceRecord.setMachine_code(Integer.valueOf(arrar[4]));
                dataList.add(attendanceRecord);
            }

            List<List<AttendanceRecord>> partition = Lists.partition(dataList, 10000);
            for (List p : partition) {
                attendanceRecordService.batchInsert(p);
            }
        } catch (Exception e) {
            log.error("上传异常", e);
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return ResultGenerator.genSuccessResult();
    }

}
