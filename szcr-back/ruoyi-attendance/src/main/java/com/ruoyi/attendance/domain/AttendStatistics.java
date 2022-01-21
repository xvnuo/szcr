package com.ruoyi.attendance.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考勤统计对象 attend_statistics
 * 
 * @author xvnuo
 * @date 2022-01-21
 */
public class AttendStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 统计编号 */
    private Long statisticsId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 规则ID */
    @Excel(name = "规则ID")
    private Long ruleId;

    /** 年份 */
    @Excel(name = "年份")
    private Long attendYear;

    /** 月份 */
    @Excel(name = "月份")
    private Long attendMonth;

    /** 应到天数 */
    @Excel(name = "应到天数")
    private Long shouldDays;

    /** 实到天数 */
    @Excel(name = "实到天数")
    private Long attendDays;

    /** 迟到次数 */
    @Excel(name = "迟到次数")
    private Long lateTimes;

    /** 早退次数 */
    @Excel(name = "早退次数")
    private Long earlyTimes;

    /** 缺勤天数 */
    @Excel(name = "缺勤天数")
    private Long absentDays;

    public void setStatisticsId(Long statisticsId) 
    {
        this.statisticsId = statisticsId;
    }

    public Long getStatisticsId() 
    {
        return statisticsId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRuleId(Long ruleId) 
    {
        this.ruleId = ruleId;
    }

    public Long getRuleId() 
    {
        return ruleId;
    }
    public void setAttendYear(Long attendYear) 
    {
        this.attendYear = attendYear;
    }

    public Long getAttendYear() 
    {
        return attendYear;
    }
    public void setAttendMonth(Long attendMonth) 
    {
        this.attendMonth = attendMonth;
    }

    public Long getAttendMonth() 
    {
        return attendMonth;
    }
    public void setShouldDays(Long shouldDays) 
    {
        this.shouldDays = shouldDays;
    }

    public Long getShouldDays() 
    {
        return shouldDays;
    }
    public void setAttendDays(Long attendDays) 
    {
        this.attendDays = attendDays;
    }

    public Long getAttendDays() 
    {
        return attendDays;
    }
    public void setLateTimes(Long lateTimes) 
    {
        this.lateTimes = lateTimes;
    }

    public Long getLateTimes() 
    {
        return lateTimes;
    }
    public void setEarlyTimes(Long earlyTimes) 
    {
        this.earlyTimes = earlyTimes;
    }

    public Long getEarlyTimes() 
    {
        return earlyTimes;
    }
    public void setAbsentDays(Long absentDays) 
    {
        this.absentDays = absentDays;
    }

    public Long getAbsentDays() 
    {
        return absentDays;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("statisticsId", getStatisticsId())
            .append("userId", getUserId())
            .append("ruleId", getRuleId())
            .append("attendYear", getAttendYear())
            .append("attendMonth", getAttendMonth())
            .append("shouldDays", getShouldDays())
            .append("attendDays", getAttendDays())
            .append("lateTimes", getLateTimes())
            .append("earlyTimes", getEarlyTimes())
            .append("absentDays", getAbsentDays())
            .toString();
    }
}
