package com.ruoyi.attendance.service;

import java.util.List;
import com.ruoyi.attendance.domain.AttendHoliday;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 节假日信息Service接口
 *
 * @author xvnuo
 * @date 2021-12-18
 */
public interface IAttendHolidayService
{
    /**
     * 查询节假日信息
     *
     * @param holidayId 节假日信息主键
     * @return 节假日信息
     */
    public AttendHoliday selectAttendHolidayByHolidayId(Long holidayId);

    /**
     * 查询节假日信息列表
     *
     * @param attendHoliday 节假日信息
     * @return 节假日信息集合
     */
    public List<AttendHoliday> selectAttendHolidayList(AttendHoliday attendHoliday);

    /**
     * 新增节假日信息
     *
     * @param attendHoliday 节假日信息
     * @return 结果
     */
    public int insertAttendHoliday(AttendHoliday attendHoliday);

    /**
     * 修改节假日信息
     *
     * @param attendHoliday 节假日信息
     * @return 结果
     */
    public int updateAttendHoliday(AttendHoliday attendHoliday);

    /**
     * 修改节假日状态
     *
     * @param holiday 节假日信息
     * @return 结果
     */
    public int updateHolidayStatus(AttendHoliday holiday);

    /**
     * 批量删除节假日信息
     *
     * @param holidayIds 需要删除的节假日信息主键集合
     * @return 结果
     */
    public int deleteAttendHolidayByHolidayIds(Long[] holidayIds);

    /**
     * 删除节假日信息信息
     *
     * @param holidayId 节假日信息主键
     * @return 结果
     */
    public int deleteAttendHolidayByHolidayId(Long holidayId);
}
