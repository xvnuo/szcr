package com.ruoyi.attendance.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 月度考勤统计对象 attend_statistics
 * 
 * @author xvnuo
 * @date 2022-01-23
 */
public class AttendStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 统计编号 */
    private Long statisticsId;

    /** 考勤月份 */
    @Excel(name = "考勤月份")
    private String attendMonth;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private Long userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 规则编号 */
    private Long ruleId;

    /** 规则名称 */
    @Excel(name = "规则名称")
    private String ruleName;

    /** 实到天数 */
    @Excel(name = "实到天数")
    private Double attendDays;

    /** 正常天数 */
    @Excel(name = "正常天数")
    private Double normalDays;

    /** 外勤天数 */
    @Excel(name = "外勤天数")
    private Double outsideDays;

    /** 缺勤天数 */
    @Excel(name = "缺勤天数")
    private Double absenceDays;

    /** 请假天数 */
    @Excel(name = "请假天数")
    private Double leaveDays;

    /** 迟到次数 */
    @Excel(name = "迟到次数")
    private Integer lateTimes;

    /** 早退次数 */
    @Excel(name = "早退次数")
    private Integer earlyTimes;

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
    public void setAttendMonth(String attendMonth) 
    {
        this.attendMonth = attendMonth;
    }

    public String getAttendMonth() 
    {
        return attendMonth;
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
    public void setAttendDays(Double attendDays) 
    {
        this.attendDays = attendDays;
    }

    public Double getAttendDays() 
    {
        return attendDays;
    }
    public void setNormalDays(Double normalDays) 
    {
        this.normalDays = normalDays;
    }

    public Double getNormalDays() 
    {
        return normalDays;
    }
    public void setOutsideDays(Double outsideDays) 
    {
        this.outsideDays = outsideDays;
    }

    public Double getOutsideDays() 
    {
        return outsideDays;
    }
    public void setAbsenceDays(Double absenceDays) 
    {
        this.absenceDays = absenceDays;
    }

    public Double getAbsenceDays() 
    {
        return absenceDays;
    }
    public void setLeaveDays(Double leaveDays) 
    {
        this.leaveDays = leaveDays;
    }

    public Double getLeaveDays() 
    {
        return leaveDays;
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
            .append("attendMonth", getAttendMonth())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("ruleId", getRuleId())
            .append("ruleName", getRuleName())
            .append("attendDays", getAttendDays())
            .append("normalDays", getNormalDays())
            .append("outsideDays", getOutsideDays())
            .append("absenceDays", getAbsenceDays())
            .append("leaveDays", getLeaveDays())
            .append("lateTimes", getLateTimes())
            .append("earlyTimes", getEarlyTimes())
            .append("overHours", getOverHours())
            .toString();
    }
}
