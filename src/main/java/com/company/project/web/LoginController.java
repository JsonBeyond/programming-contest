package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.BasEmp;
import com.company.project.service.BasEmpService;
import com.company.project.util.Constance;
import com.company.project.util.JwtUtils;
import com.company.project.util.RedisUtils;
import com.pagoda.common.utils.poi.ExcelImporter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author qxs on 2019/7/20.
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private BasEmpService basEmpService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/{username}")
    @ResponseBody
    public Result login(@PathVariable String username) throws Exception {
        BasEmp user = basEmpService.login(username);
        if (user == null) {
            return ResultGenerator.genFailResult("用户名或密码错误");
        }
        String token = JwtUtils.sign(user,  24L * 3600L * 1000L);
        redisUtils.setNx(Constance.LOGIN_KEY + token,"1",1, TimeUnit.DAYS);
        return ResultGenerator.genSuccessResult(token);
    }

    @PostMapping("/upload")
    public Result uploadChannel(HttpServletRequest request) {
        List<List<Object>> smsChannelList;
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("filename");
            if (file.isEmpty()) {
                return ResultGenerator.genFailResult("文件不能为空");
            }
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            smsChannelList = ExcelImporter.getListByExcel(inputStream, fileName);
            inputStream.close();
        } catch (Exception e) {
            return ResultGenerator.genFailResult("短信通道上传异常");
        }
        return ResultGenerator.genSuccessResult();
    }
}
