package com.ruoyi.attendance.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.attendance.domain.AttendRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 考勤记录Mapper接口
 *
 * @author xvnuo
 * @date 2022-02-09
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

    /**
     * 判断数据库中是否已经有同一个用户当天的记录了
     * @param userId 用户ID
     * @param attendDate 考勤日期
     * @return 结果
     */
    public AttendRecord checkDuplicate(@Param("userId") Long userId, @Param("attendDate") Date attendDate);
}
