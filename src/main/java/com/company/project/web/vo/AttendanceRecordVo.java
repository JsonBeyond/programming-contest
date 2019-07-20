package com.company.project.web.vo;

import lombok.Data;
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
@Data
public class AttendanceRecordVo {

    /**
     * 部门名稱
     */
    private String department;

    /**
     * 员工名称
     */
    private String userName;

    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
}
