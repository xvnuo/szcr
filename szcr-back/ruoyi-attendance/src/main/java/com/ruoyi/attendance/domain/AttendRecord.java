package com.ruoyi.attendance.domain;

import java.sql.Time;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考勤记录对象 attend_record
 *
 * @author xvnuo
 * @date 2022-01-20
 */
public class AttendRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    @Excel(name = "记录ID")
    private Long recordId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 考勤日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考勤日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date attendDate;

    /** 年份 */
    private Integer attendYear;

    /** 月份 */
    private Integer attendMonth;

    /** 考勤状态（0正常 1停用） */
    @Excel(name = "考勤状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 上班时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "上班时间", width = 30, dateFormat = "HH:mm:ss")
    private Time onTime;

    /** 下班时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "下班时间", width = 30, dateFormat = "HH:mm:ss")
    private Time offTime;

    public void setRecordId(Long recordId)
    {
        this.recordId = recordId;
    }

    public Long getRecordId()
    {
        return recordId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setAttendDate(Date attendDate)
    {
        this.attendDate = attendDate;
    }

    public Date getAttendDate()
    {
        return attendDate;
    }
    public void setAttendYear(Integer attendYear)
    {
        this.attendYear = attendYear;
    }

    public Integer getAttendYear()
    {
        return attendYear;
    }
    public void setAttendMonth(Integer attendMonth)
    {
        this.attendMonth = attendMonth;
    }

    public Integer getAttendMonth()
    {
        return attendMonth;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setOnTime(Time onTime)
    {
        this.onTime = onTime;
    }

    public Date getOnTime()
    {
        return onTime;
    }
    public void setOffTime(Time offTime)
    {
        this.offTime = offTime;
    }

    public Date getOffTime()
    {
        return offTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("userId", getUserId())
            .append("attendDate", getAttendDate())
            .append("attendYear", getAttendYear())
            .append("attendMonth", getAttendMonth())
            .append("status", getStatus())
            .append("onTime", getOnTime())
            .append("offTime", getOffTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
