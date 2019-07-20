package com.company.project.model;

import javax.persistence.*;

@Table(name = "bas_emp")
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

    /**
     * 获取员工Id[emp_id]
     *
     * @return id - 员工Id[emp_id]
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置员工Id[emp_id]
     *
     * @param id 员工Id[emp_id]
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取员工编码[emp_code]
     *
     * @return code - 员工编码[emp_code]
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置员工编码[emp_code]
     *
     * @param code 员工编码[emp_code]
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取员工名称[emp_name]
     *
     * @return name - 员工名称[emp_name]
     */
    public String getName() {
        return name;
    }

    /**
     * 设置员工名称[emp_name]
     *
     * @param name 员工名称[emp_name]
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取所属部门
     *
     * @return dep_id - 所属部门
     */
    public Long getDep_id() {
        return dep_id;
    }

    /**
     * 设置所属部门
     *
     * @param dep_id 所属部门
     */
    public void setDep_id(Long dep_id) {
        this.dep_id = dep_id;
    }

    /**
     * 获取所属部门编码[冗余]
     *
     * @return dep_code - 所属部门编码[冗余]
     */
    public String getDep_code() {
        return dep_code;
    }

    /**
     * 设置所属部门编码[冗余]
     *
     * @param dep_code 所属部门编码[冗余]
     */
    public void setDep_code(String dep_code) {
        this.dep_code = dep_code;
    }

    /**
     * 获取所属部门名称[冗余]
     *
     * @return dep_name - 所属部门名称[冗余]
     */
    public String getDep_name() {
        return dep_name;
    }

    /**
     * 设置所属部门名称[冗余]
     *
     * @param dep_name 所属部门名称[冗余]
     */
    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }
}