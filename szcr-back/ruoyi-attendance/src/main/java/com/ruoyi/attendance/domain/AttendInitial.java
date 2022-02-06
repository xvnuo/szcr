package com.ruoyi.attendance.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原始考勤记录对象 attend_initial
 *
 * @author xvnuo
 * @date 2022-02-06
 */
public class AttendInitial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 初始编号 */
    private Long initialId;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private Long userId;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 打卡时间 */
    @Excel(name = "打卡时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date initialTime;

    /** 打卡地点 */
    @Excel(name = "打卡地点")
    private String initialPlace;

    public void setInitialId(Long initialId)
    {
        this.initialId = initialId;
    }

    public Long getInitialId()
    {
        return initialId;
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
    public void setInitialTime(Date initialTime)
    {
        this.initialTime = initialTime;
    }

    public Date getInitialTime()
    {
        return initialTime;
    }
    public void setInitialPlace(String initialPlace)
    {
        this.initialPlace = initialPlace;
    }

    public String getInitialPlace()
    {
        return initialPlace;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("initialId", getInitialId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("initialTime", getInitialTime())
            .append("initialPlace", getInitialPlace())
            .toString();
    }
}
