package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.BasEmp;
import com.company.project.service.BasEmpService;
import com.company.project.util.Constance;
import com.company.project.util.JwtUtils;
import com.company.project.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
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

    @PostMapping("/{username}")
    @ResponseBody
    public Result login(@PathVariable String username) throws Exception {
        BasEmp user = basEmpService.login(username);
        if (user == null) {
            return ResultGenerator.genFailResult("用户名或密码错误");
        }
        String token = JwtUtils.sign(user, 24L * 3600L * 1000L);
        redisUtils.setNx(Constance.LOGIN_KEY + token, "1", 1, TimeUnit.DAYS);
        return ResultGenerator.genSuccessResult(token);
    }

    @PostMapping(value = "/upload")
    public Result uploadChannel(HttpServletRequest request) throws IOException {
        List<String> dataList = new ArrayList<String>();
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
            while ((line = br.readLine()) != null) {
                dataList.add(line);
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
