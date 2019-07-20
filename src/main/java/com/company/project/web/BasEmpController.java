package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.BasEmp;
import com.company.project.service.BasEmpService;
import com.company.project.util.Constance;
import com.company.project.util.JwtUtils;
import com.company.project.util.RedisUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* Created by CodeGenerator on 2019/07/20.
*/
@RestController
@RequestMapping("/bas/emp")
public class BasEmpController {
    @Resource
    private BasEmpService basEmpService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/add")
    public Result add(BasEmp basEmp) {
        basEmpService.save(basEmp);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        basEmpService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(BasEmp basEmp) {
        basEmpService.update(basEmp);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        BasEmp basEmp = basEmpService.findById(id);
        return ResultGenerator.genSuccessResult(basEmp);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<BasEmp> list = basEmpService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/login/{username}")
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
}
