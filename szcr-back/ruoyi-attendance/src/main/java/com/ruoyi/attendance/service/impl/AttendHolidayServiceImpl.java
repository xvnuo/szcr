package com.ruoyi.attendance.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
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
        return attendHolidayMapper.deleteAttendHolidayByHolidayId(holidayId);
    }
}
