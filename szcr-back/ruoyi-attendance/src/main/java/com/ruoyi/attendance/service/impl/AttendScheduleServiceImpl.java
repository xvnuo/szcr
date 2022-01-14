package com.ruoyi.attendance.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.AttendSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.attendance.mapper.AttendScheduleMapper;
import com.ruoyi.attendance.service.IAttendScheduleService;

/**
 * 排班Service业务层处理
 *
 * @author xvnuo
 * @date 2021-12-19
 */
@Service
public class AttendScheduleServiceImpl implements IAttendScheduleService
{
    @Autowired
    private AttendScheduleMapper attendScheduleMapper;

    /**
     * 查询排班
     *
     * @param scheduleId 排班主键
     * @return 排班
     */
    @Override
    public AttendSchedule selectAttendScheduleByScheduleId(Long scheduleId)
    {
        return attendScheduleMapper.selectAttendScheduleByScheduleId(scheduleId);
    }

    /**
     * 查询排班列表
     *
     * @param attendSchedule 排班
     * @return 排班
     */
    @Override
    public List<AttendSchedule> selectAttendScheduleList(AttendSchedule attendSchedule)
    {
        return attendScheduleMapper.selectAttendScheduleList(attendSchedule);
    }

    /**
     * 新增排班
     *
     * @param attendSchedule 排班
     * @return 结果
     */
    @Override
    public int insertAttendSchedule(AttendSchedule attendSchedule)
    {
        return attendScheduleMapper.insertAttendSchedule(attendSchedule);
    }

    /**
     * 修改排班
     *
     * @param attendSchedule 排班
     * @return 结果
     */
    @Override
    public int updateAttendSchedule(AttendSchedule attendSchedule)
    {
        return attendScheduleMapper.updateAttendSchedule(attendSchedule);
    }

    /**
     * 批量删除排班
     *
     * @param scheduleIds 需要删除的排班主键
     * @return 结果
     */
    @Override
    public int deleteAttendScheduleByScheduleIds(Long[] scheduleIds)
    {
        return attendScheduleMapper.deleteAttendScheduleByScheduleIds(scheduleIds);
    }

    /**
     * 删除排班信息
     *
     * @param scheduleId 排班主键
     * @return 结果
     */
    @Override
    public int deleteAttendScheduleByScheduleId(Long scheduleId)
    {
        return attendScheduleMapper.deleteAttendScheduleByScheduleId(scheduleId);
    }
}
