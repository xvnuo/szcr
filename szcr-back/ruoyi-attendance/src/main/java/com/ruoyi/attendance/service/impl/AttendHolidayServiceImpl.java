package com.ruoyi.attendance.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.attendance.mapper.AttendHolidayMapper;
import com.ruoyi.attendance.domain.AttendHoliday;
import com.ruoyi.attendance.service.IAttendHolidayService;

/**
 * 节假日信息Service业务层处理
 *
 * @author xvnuo
 * @date 2021-12-18
 */
@Service
public class AttendHolidayServiceImpl implements IAttendHolidayService
{
    @Autowired
    private AttendHolidayMapper attendHolidayMapper;

    /**
     * 查询节假日信息
     *
     * @param holidayId 节假日信息主键
     * @return 节假日信息
     */
    @Override
    public AttendHoliday selectAttendHolidayByHolidayId(Long holidayId)
    {
        return attendHolidayMapper.selectAttendHolidayByHolidayId(holidayId);
    }

    /**
     * 查询节假日信息列表
     *
     * @param attendHoliday 节假日信息
     * @return 节假日信息
     */
    @Override
    public List<AttendHoliday> selectAttendHolidayList(AttendHoliday attendHoliday)
    {
        return attendHolidayMapper.selectAttendHolidayList(attendHoliday);
    }

    /**
     * 新增节假日信息
     *
     * @param attendHoliday 节假日信息
     * @return 结果
     */
    @Override
    public int insertAttendHoliday(AttendHoliday attendHoliday)
    {
        attendHoliday.setCreateTime(DateUtils.getNowDate());
        // 将给定区间内的日期都设置为“3”-休假日
        attendHolidayMapper.updateCalendar(attendHoliday.getBeginTime(), attendHoliday.getEndTime(), "3");
        return attendHolidayMapper.insertAttendHoliday(attendHoliday);
    }

    /**
     * 修改节假日信息
     *
     * @param attendHoliday 节假日信息
     * @return 结果
     */
    @Override
    public int updateAttendHoliday(AttendHoliday attendHoliday)
    {
        // 需要有旧的holiday信息，才能正确更新
        attendHoliday.setUpdateTime(DateUtils.getNowDate());
        return attendHolidayMapper.updateAttendHoliday(attendHoliday);
    }

    /**
     * 修改节假日状态
     *
     * @param holiday 节假日信息
     * @return 结果
     */
    @Override
    public int updateHolidayStatus(AttendHoliday holiday)
    {
        AttendHoliday oholiday = attendHolidayMapper.selectAttendHolidayByHolidayId(holiday.getHolidayId());
        // 0-启用假期
        if(holiday.getStatus().equals("0")){
            attendHolidayMapper.updateCalendar(oholiday.getBeginTime(), oholiday.getEndTime(), "3");
        }
        // 1-停用假期
        else{
            attendHolidayMapper.updateCalendar(oholiday.getBeginTime(), oholiday.getEndTime(), "0");
        }
        return attendHolidayMapper.updateAttendHoliday(holiday);
    }

    /**
     * 批量删除节假日信息
     *
     * @param holidayIds 需要删除的节假日信息主键
     * @return 结果
     */
    @Override
    public int deleteAttendHolidayByHolidayIds(Long[] holidayIds)
    {
        for(Long holidayId: holidayIds){
            // 将该假期范围内的状态设置为0(暂时不区分工作日和周末)
            AttendHoliday holiday = attendHolidayMapper.selectAttendHolidayByHolidayId(holidayId);
            attendHolidayMapper.updateCalendar(holiday.getBeginTime(), holiday.getEndTime(), "0");
        }
        return attendHolidayMapper.deleteAttendHolidayByHolidayIds(holidayIds);
    }

    /**
     * 删除节假日信息信息
     *
     * @param holidayId 节假日信息主键
     * @return 结果
     */
    @Override
    public int deleteAttendHolidayByHolidayId(Long holidayId)
    {
        // 将该假期范围内的状态设置为0(暂时不区分工作日和周末)
        AttendHoliday holiday = attendHolidayMapper.selectAttendHolidayByHolidayId(holidayId);
        attendHolidayMapper.updateCalendar(holiday.getBeginTime(), holiday.getEndTime(), "0");
        return attendHolidayMapper.deleteAttendHolidayByHolidayId(holidayId);
    }
}
