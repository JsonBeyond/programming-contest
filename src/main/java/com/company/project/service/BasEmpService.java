package com.company.project.service;
import com.company.project.model.BasEmp;
import com.company.project.core.Service;


/**
 * Created by CodeGenerator on 2019/07/20.
 */
public interface BasEmpService extends Service<BasEmp> {

    BasEmp login(String username);
}
