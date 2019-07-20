package com.company.project.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "bas_emp")
@Data
public class BasEmp {
    /**
     * 员工Id[emp_id]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 员工编码[emp_code]
     */
    private String code;

    /**
     * 员工名称[emp_name]
     */
    private String name;

    /**
     * 所属部门
     */
    private Long dep_id;

    /**
     * 所属部门编码[冗余]
     */
    private String dep_code;

    /**
     * 所属部门名称[冗余]
     */
    private String dep_name;

}