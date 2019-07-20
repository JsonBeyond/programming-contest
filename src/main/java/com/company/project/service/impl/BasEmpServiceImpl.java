package com.company.project.service.impl;

import com.company.project.dao.BasEmpMapper;
import com.company.project.model.BasEmp;
import com.company.project.service.BasEmpService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
@Service
@Transactional
public class BasEmpServiceImpl extends AbstractService<BasEmp> implements BasEmpService {
    @Resource
    private BasEmpMapper basEmpMapper;

}
