package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "attendance_result")
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

    /**
     * 获取考勤记录ID
     *
     * @return id - 考勤记录ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置考勤记录ID
     *
     * @param id 考勤记录ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取部门代码
     *
     * @return dep_code - 部门代码
     */
    public String getDep_code() {
        return dep_code;
    }

    /**
     * 设置部门代码
     *
     * @param dep_code 部门代码
     */
    public void setDep_code(String dep_code) {
        this.dep_code = dep_code;
    }

    /**
     * 获取员工名称
     *
     * @return staff_name - 员工名称
     */
    public String getStaff_name() {
        return staff_name;
    }

    /**
     * 设置员工名称
     *
     * @param staff_name 员工名称
     */
    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    /**
     * 获取考勤号码
     *
     * @return attendance_num - 考勤号码
     */
    public Integer getAttendance_num() {
        return attendance_num;
    }

    /**
     * 设置考勤号码
     *
     * @param attendance_num 考勤号码
     */
    public void setAttendance_num(Integer attendance_num) {
        this.attendance_num = attendance_num;
    }

    /**
     * 获取最早时间
     *
     * @return attendance_start_time - 最早时间
     */
    public Date getAttendance_start_time() {
        return attendance_start_time;
    }

    /**
     * 设置最早时间
     *
     * @param attendance_start_time 最早时间
     */
    public void setAttendance_start_time(Date attendance_start_time) {
        this.attendance_start_time = attendance_start_time;
    }

    /**
     * 获取最晚时间
     *
     * @return attendance_end_time - 最晚时间
     */
    public Date getAttendance_end_time() {
        return attendance_end_time;
    }

    /**
     * 设置最晚时间
     *
     * @param attendance_end_time 最晚时间
     */
    public void setAttendance_end_time(Date attendance_end_time) {
        this.attendance_end_time = attendance_end_time;
    }

    /**
     * 获取机器号
     *
     * @return machine_code - 机器号
     */
    public Integer getMachine_code() {
        return machine_code;
    }

    /**
     * 设置机器号
     *
     * @param machine_code 机器号
     */
    public void setMachine_code(Integer machine_code) {
        this.machine_code = machine_code;
    }
}