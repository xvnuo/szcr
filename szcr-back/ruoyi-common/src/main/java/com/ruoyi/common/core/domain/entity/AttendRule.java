package com.ruoyi.common.core.domain.entity;

import java.sql.Time;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考勤规则对象 attend_rule
 *
 * @author xvnuo
 * @date 2021-12-19
 */
public class AttendRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long ruleId;

    /** 名称 */
    @Excel(name = "名称")
    private String ruleName;

    /** 考勤类型（1固定 2弹性 3排班） */
    @Excel(name = "考勤类型", readConverterExp = "1=固定,2=弹性,3=排班")
    private String ruleType;

    /** 上班时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "上班时间", width = 30, dateFormat = "HH:mm:ss")
    private Time onTime;

    /** 下班时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "下班时间", width = 30, dateFormat = "HH:mm:ss")
    private Time offTime;

    /** 每日工时 */
    @Excel(name = "每日工时")
    private Long workHour;

    /** 工作日 */
    @Excel(name = "工作日")
    private String workDays;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 排班信息 */
    private List<AttendSchedule> attendScheduleList;

    public void setRuleId(Long ruleId)
    {
        this.ruleId = ruleId;
    }

    public Long getRuleId()
    {
        return ruleId;
    }
    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }

    public String getRuleName()
    {
        return ruleName;
    }
    public void setRuleType(String ruleType)
    {
        this.ruleType = ruleType;
    }

    public String getRuleType()
    {
        return ruleType;
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
    public void setWorkHour(Long workHour)
    {
        this.workHour = workHour;
    }

    public Long getWorkHour()
    {
        return workHour;
    }
    public void setWorkDays(String workDays)
    {
        this.workDays = workDays;
    }

    public String getWorkDays()
    {
        return workDays;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public List<AttendSchedule> getAttendScheduleList()
    {
        return attendScheduleList;
    }

    public void setAttendScheduleList(List<AttendSchedule> attendScheduleList)
    {
        this.attendScheduleList = attendScheduleList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ruleId", getRuleId())
            .append("ruleName", getRuleName())
            .append("ruleType", getRuleType())
            .append("onTime", getOnTime())
            .append("offTime", getOffTime())
            .append("workHour", getWorkHour())
            .append("workDays", getWorkDays())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("attendScheduleList", getAttendScheduleList())
            .toString();
    }
}
