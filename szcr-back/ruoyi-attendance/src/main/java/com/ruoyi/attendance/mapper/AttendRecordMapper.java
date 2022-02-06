package com.ruoyi.attendance.mapper;

import java.util.List;
import com.ruoyi.attendance.domain.AttendRecord;
import com.ruoyi.attendance.domain.AttendStatistics;

/**
 * 考勤记录Mapper接口
 *
 * @author xvnuo
 * @date 2022-01-23
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
     * 根据用户名、月份生成月度考勤统计
     * @param statistics 用户名、月份条件
     * @return 结果
     */
    public List<AttendStatistics> generateStatisticsList(AttendStatistics statistics);
}
