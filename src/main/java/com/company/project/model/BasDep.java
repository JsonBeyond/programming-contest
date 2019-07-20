package com.company.project.model;

import javax.persistence.*;

@Table(name = "bas_dep")
public class BasDep {
    /**
     * 部门ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 部门代码
     */
    private String code;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级部门ID 
     */
    private Long parent_id;

    /**
     * 获取部门ID
     *
     * @return id - 部门ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置部门ID
     *
     * @param id 部门ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取部门代码
     *
     * @return code - 部门代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置部门代码
     *
     * @param code 部门代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取部门名称
     *
     * @return name - 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置部门名称
     *
     * @param name 部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取上级部门ID 
     *
     * @return parent_id - 上级部门ID 
     */
    public Long getParent_id() {
        return parent_id;
    }

    /**
     * 设置上级部门ID 
     *
     * @param parent_id 上级部门ID 
     */
    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }
}