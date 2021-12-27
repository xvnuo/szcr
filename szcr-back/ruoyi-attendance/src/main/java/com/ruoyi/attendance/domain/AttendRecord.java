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
 * @date 2021-12-18
 */
public class AttendRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 考勤ID */
    private Long recordId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 考勤状态（0正常 1停用） */
    @Excel(name = "考勤状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 考勤日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考勤日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

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
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }

    public Date getDate()
    {
        return date;
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
            .append("status", getStatus())
            .append("date", getDate())
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
