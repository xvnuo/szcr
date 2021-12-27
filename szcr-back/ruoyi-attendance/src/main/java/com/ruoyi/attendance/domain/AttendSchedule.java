package com.ruoyi.attendance.domain;

import java.sql.Time;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 排班对象 attend_schedule
 *
 * @author xvnuo
 * @date 2021-12-19
 */
public class AttendSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long scheduleId;

    /** 规则ID */
    @Excel(name = "规则ID")
    private Long ruleId;

    /** 班次名称 */
    @Excel(name = "班次名称")
    private String scheduleName;

    /** 工作日 */
    @Excel(name = "工作日")
    private Long workDay;

    /** 开始时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "HH:mm:ss")
    private Time onTime;

    /** 结束时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "HH:mm:ss")
    private Time offTime;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setScheduleId(Long scheduleId)
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId()
    {
        return scheduleId;
    }
    public void setRuleId(Long ruleId)
    {
        this.ruleId = ruleId;
    }

    public Long getRuleId()
    {
        return ruleId;
    }
    public void setScheduleName(String scheduleName)
    {
        this.scheduleName = scheduleName;
    }

    public String getScheduleName()
    {
        return scheduleName;
    }
    public void setWorkDay(Long workDay)
    {
        this.workDay = workDay;
    }

    public Long getWorkDay()
    {
        return workDay;
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
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("scheduleId", getScheduleId())
            .append("ruleId", getRuleId())
            .append("scheduleName", getScheduleName())
            .append("workDay", getWorkDay())
            .append("onTime", getOnTime())
            .append("offTime", getOffTime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .toString();
    }
}
