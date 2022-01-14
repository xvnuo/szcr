package com.ruoyi.attendance.service;

import java.util.List;
import com.ruoyi.attendance.domain.AttendStatistics;

/**
 * 考勤统计Service接口
 * 
 * @author xvnuo
 * @date 2022-01-12
 */
public interface IAttendStatisticsService 
{
    /**
     * 查询考勤统计
     * 
     * @param statisticsId 考勤统计主键
     * @return 考勤统计
     */
    public AttendStatistics selectAttendStatisticsByStatisticsId(Long statisticsId);

    /**
     * 查询考勤统计列表
     * 
     * @param attendStatistics 考勤统计
     * @return 考勤统计集合
     */
    public List<AttendStatistics> selectAttendStatisticsList(AttendStatistics attendStatistics);

    /**
     * 新增考勤统计
     * 
     * @param attendStatistics 考勤统计
     * @return 结果
     */
    public int insertAttendStatistics(AttendStatistics attendStatistics);

    /**
     * 修改考勤统计
     * 
     * @param attendStatistics 考勤统计
     * @return 结果
     */
    public int updateAttendStatistics(AttendStatistics attendStatistics);

    /**
     * 批量删除考勤统计
     * 
     * @param statisticsIds 需要删除的考勤统计主键集合
     * @return 结果
     */
    public int deleteAttendStatisticsByStatisticsIds(Long[] statisticsIds);

    /**
     * 删除考勤统计信息
     * 
     * @param statisticsId 考勤统计主键
     * @return 结果
     */
    public int deleteAttendStatisticsByStatisticsId(Long statisticsId);
}