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
 * @date 2022-01-23
 */
public class AttendRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 规则编号 */
    private Long ruleId;

    /** 规则名称 */
    @Excel(name = "规则名称")
    private String ruleName;

    /** 考勤日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考勤日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date attendDate;

    /** 星期几 */
    @Excel(name = "星期几")
    private Integer weekdayNum;

    /** 是否工作日 */
    @Excel(name = "是否工作日")
    private String isWorkday;

    /** 考勤月份 */
    @Excel(name = "考勤月份")
    private String attendMonth;

    /** 考勤时长 */
    @Excel(name = "考勤时长")
    private Double attendHour;

    /** 排班序号 */
    @Excel(name = "排班序号")
    private Integer scheduleSort;

    /** 上班时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "上班时间", width = 30, dateFormat = "HH:mm:ss")
    private Time onTime;

    /** 下班时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "下班时间", width = 30, dateFormat = "HH:mm:ss")
    private Time offTime;

    /** 上班状态 */
    @Excel(name = "上班状态")
    private String onStatus;

    /** 下班状态 */
    @Excel(name = "下班状态")
    private String offStatus;

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
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
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
    public void setAttendDate(Date attendDate)
    {
        this.attendDate = attendDate;
    }

    public Date getAttendDate()
    {
        return attendDate;
    }
    public void setWeekdayNum(Integer weekdayNum)
    {
        this.weekdayNum = weekdayNum;
    }

    public Integer getWeekdayNum()
    {
        return weekdayNum;
    }
    public void setIsWorkday(String isWorkday)
    {
        this.isWorkday = isWorkday;
    }

    public String getIsWorkday()
    {
        return isWorkday;
    }
    public void setAttendMonth(String attendMonth)
    {
        this.attendMonth = attendMonth;
    }

    public String getAttendMonth()
    {
        return attendMonth;
    }
    public void setAttendHour(Double attendHour)
    {
        this.attendHour = attendHour;
    }

    public Double getAttendHour()
    {
        return attendHour;
    }
    public void setScheduleSort(Integer scheduleSort)
    {
        this.scheduleSort = scheduleSort;
    }

    public Integer getScheduleSort()
    {
        return scheduleSort;
    }
    public void setOnTime(Time onTime)
    {
        this.onTime = onTime;
    }

    public Time getOnTime()
    {
        return onTime;
    }
    public void setOnStatus(String onStatus)
    {
        this.onStatus = onStatus;
    }

    public String getOnStatus()
    {
        return onStatus;
    }
    public void setOffTime(Time offTime)
    {
        this.offTime = offTime;
    }

    public Time getOffTime()
    {
        return offTime;
    }
    public void setOffStatus(String offStatus)
    {
        this.offStatus = offStatus;
    }

    public String getOffStatus()
    {
        return offStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("attendDate", getAttendDate())
            .append("weekdayNum", getWeekdayNum())
            .append("isWorkday", getIsWorkday())
            .append("attendMonth", getAttendMonth())
            .append("attendHour", getAttendHour())
            .append("scheduleSort", getScheduleSort())
            .append("onTime", getOnTime())
            .append("onStatus", getOnStatus())
            .append("offTime", getOffTime())
            .append("offStatus", getOffStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
