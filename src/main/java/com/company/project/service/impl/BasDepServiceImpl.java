package com.company.project.service.impl;

import com.company.project.dao.BasDepMapper;
import com.company.project.model.BasDep;
import com.company.project.service.BasDepService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
@Service
@Transactional
public class BasDepServiceImpl extends AbstractService<BasDep> implements BasDepService {
    @Resource
    private BasDepMapper basDepMapper;

}
