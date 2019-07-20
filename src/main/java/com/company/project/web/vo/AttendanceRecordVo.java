package com.company.project.web.vo;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName AttendanceRecordVo
 * @Description TODO
 * @Author Alex
 * @CreateDate 2019/7/20 11:50
 * @Version 1.0
 */
public class AttendanceRecordVo {

    /**
     * 部门代码
     */
    @Excel(name = "dep_code",width = 15)
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
     * 日期时间
     */
    private Date attendance_time;

    /**
     * 机器号
     */
    private Integer machine_code;

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
     * 获取日期时间
     *
     * @return attendance_time - 日期时间
     */
    public Date getAttendance_time() {
        return attendance_time;
    }

    /**
     * 设置日期时间
     *
     * @param attendance_time 日期时间
     */
    public void setAttendance_time(Date attendance_time) {
        this.attendance_time = attendance_time;
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
