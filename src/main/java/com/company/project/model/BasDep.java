package com.company.project.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "bas_dep")
@Data
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

}