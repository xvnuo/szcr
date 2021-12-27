package com.ruoyi.attendance.mapper;

import java.util.List;
import com.ruoyi.attendance.domain.AttendSchedule;

/**
 * 排班Mapper接口
 * 
 * @author xvnuo
 * @date 2021-12-19
 */
public interface AttendScheduleMapper 
{
    /**
     * 查询排班
     * 
     * @param scheduleId 排班主键
     * @return 排班
     */
    public AttendSchedule selectAttendScheduleByScheduleId(Long scheduleId);

    /**
     * 查询排班列表
     * 
     * @param attendSchedule 排班
     * @return 排班集合
     */
    public List<AttendSchedule> selectAttendScheduleList(AttendSchedule attendSchedule);

    /**
     * 新增排班
     * 
     * @param attendSchedule 排班
     * @return 结果
     */
    public int insertAttendSchedule(AttendSchedule attendSchedule);

    /**
     * 修改排班
     * 
     * @param attendSchedule 排班
     * @return 结果
     */
    public int updateAttendSchedule(AttendSchedule attendSchedule);

    /**
     * 删除排班
     * 
     * @param scheduleId 排班主键
     * @return 结果
     */
    public int deleteAttendScheduleByScheduleId(Long scheduleId);

    /**
     * 批量删除排班
     * 
     * @param scheduleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttendScheduleByScheduleIds(Long[] scheduleIds);
}
