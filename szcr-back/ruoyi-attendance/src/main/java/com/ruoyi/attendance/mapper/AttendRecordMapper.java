package com.ruoyi.attendance.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.attendance.domain.AttendRecord;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 考勤记录Mapper接口
 *
 * @author xvnuo
 * @date 2022-01-20
 */
public interface AttendRecordMapper
{
    /**
     * 查询考勤记录
     *
     * @param recordId 考勤记录主键
     * @return 考勤记录
     */
    public AttendRecord selectAttendRecordByRecordId(Long recordId);

    /**
     * 根据用户和日期查询
     * @param userId 用户ID
     * @param date  考勤日期
     * @return 考勤记录
     */
    public AttendRecord selectByUserIdAndDate(@Param("userId") Long userId, @Param("date") Date date);

    /**
     * 查询考勤记录列表
     *
     * @param attendRecord 考勤记录
     * @return 考勤记录集合
     */
    public List<AttendRecord> selectAttendRecordList(AttendRecord attendRecord);

    /**
     * 新增考勤记录
     *
     * @param attendRecord 考勤记录
     * @return 结果
     */
    public int insertAttendRecord(AttendRecord attendRecord);

    /**
     * 修改考勤记录
     *
     * @param attendRecord 考勤记录
     * @return 结果
     */
    public int updateAttendRecord(AttendRecord attendRecord);

    /**
     * 删除考勤记录
     *
     * @param recordId 考勤记录主键
     * @return 结果
     */
    public int deleteAttendRecordByRecordId(Long recordId);

    /**
     * 批量删除考勤记录
     *
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttendRecordByRecordIds(Long[] recordIds);
}
