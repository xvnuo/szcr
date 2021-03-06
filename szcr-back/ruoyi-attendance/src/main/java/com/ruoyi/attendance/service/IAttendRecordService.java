package com.ruoyi.attendance.service;

import java.util.List;
import com.ruoyi.attendance.domain.AttendRecord;

/**
 * 考勤记录Service接口
 *
 * @author xvnuo
 * @date 2022-02-09
 */
public interface IAttendRecordService
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
     * 批量删除考勤记录
     *
     * @param recordIds 需要删除的考勤记录主键集合
     * @return 结果
     */
    public int deleteAttendRecordByRecordIds(Long[] recordIds);

    /**
     * 删除考勤记录信息
     *
     * @param recordId 考勤记录主键
     * @return 结果
     */
    public int deleteAttendRecordByRecordId(Long recordId);

    /**
     * 更新到今天为止的考勤记录
     */
    public void updateAllAttendRecord();
}
