package com.company.project.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "attendance_result")
@Data
public class AttendanceResult {
    /**
     * 考勤记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 部门代码
     */
    private String dep_code;

    /**
     * 员工名称
     */
    private String staff_name;

    /**
     * 考勤号码
     */
    private Integer attendance_num;

    /**
     * 最早时间
     */
    private Date attendance_start_time;

    /**
     * 最晚时间
     */
    private Date attendance_end_time;

    /**
     * 机器号
     */
    private Integer machine_code;

    private String status;
}