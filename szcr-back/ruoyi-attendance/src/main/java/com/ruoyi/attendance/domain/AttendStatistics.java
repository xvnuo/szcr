package com.ruoyi.attendance.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考勤统计对象 attend_statistics
 * 
 * @author xvnuo
 * @date 2022-01-12
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
    private Integer year;

    /** 月份 */
    @Excel(name = "月份")
    private Integer month;

    /** 应到天数 */
    @Excel(name = "应到天数")
    private Double shouldDays;

    /** 实到天数 */
    @Excel(name = "实到天数")
    private Double attendDays;

    /** 迟到次数 */
    @Excel(name = "迟到次数")
    private Integer lateTimes;

    /** 早退次数 */
    @Excel(name = "早退次数")
    private Integer earlyTimes;

    /** 缺勤天数 */
    @Excel(name = "缺勤天数")
    private Double absentDays;

    /** 外勤天数 */
    @Excel(name = "外勤天数")
    private Double outsideDays;

    /** 加班时长 */
    @Excel(name = "加班时长")
    private Double overHours;

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
    public void setYear(Integer year) 
    {
        this.year = year;
    }

    public Integer getYear() 
    {
        return year;
    }
    public void setMonth(Integer month) 
    {
        this.month = month;
    }

    public Integer getMonth() 
    {
        return month;
    }
    public void setShouldDays(Double shouldDays) 
    {
        this.shouldDays = shouldDays;
    }

    public Double getShouldDays() 
    {
        return shouldDays;
    }
    public void setAttendDays(Double attendDays) 
    {
        this.attendDays = attendDays;
    }

    public Double getAttendDays() 
    {
        return attendDays;
    }
    public void setLateTimes(Integer lateTimes) 
    {
        this.lateTimes = lateTimes;
    }

    public Integer getLateTimes() 
    {
        return lateTimes;
    }
    public void setEarlyTimes(Integer earlyTimes) 
    {
        this.earlyTimes = earlyTimes;
    }

    public Integer getEarlyTimes() 
    {
        return earlyTimes;
    }
    public void setAbsentDays(Double absentDays) 
    {
        this.absentDays = absentDays;
    }

    public Double getAbsentDays() 
    {
        return absentDays;
    }
    public void setOutsideDays(Double outsideDays) 
    {
        this.outsideDays = outsideDays;
    }

    public Double getOutsideDays() 
    {
        return outsideDays;
    }
    public void setOverHours(Double overHours) 
    {
        this.overHours = overHours;
    }

    public Double getOverHours() 
    {
        return overHours;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("statisticsId", getStatisticsId())
            .append("userId", getUserId())
            .append("ruleId", getRuleId())
            .append("year", getYear())
            .append("month", getMonth())
            .append("shouldDays", getShouldDays())
            .append("attendDays", getAttendDays())
            .append("lateTimes", getLateTimes())
            .append("earlyTimes", getEarlyTimes())
            .append("absentDays", getAbsentDays())
            .append("outsideDays", getOutsideDays())
            .append("overHours", getOverHours())
            .append("remark", getRemark())
            .toString();
    }
}
