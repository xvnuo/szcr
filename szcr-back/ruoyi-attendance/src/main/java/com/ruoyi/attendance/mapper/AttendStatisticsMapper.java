package com.ruoyi.attendance.mapper;

import java.util.List;
import com.ruoyi.attendance.domain.AttendStatistics;

/**
 * 月度考勤统计Mapper接口
 * 
 * @author xvnuo
 * @date 2022-01-23
 */
public interface AttendStatisticsMapper 
{
    /**
     * 查询月度考勤统计
     * 
     * @param statisticsId 月度考勤统计主键
     * @return 月度考勤统计
     */
    public AttendStatistics selectAttendStatisticsByStatisticsId(Long statisticsId);

    /**
     * 查询月度考勤统计列表
     * 
     * @param attendStatistics 月度考勤统计
     * @return 月度考勤统计集合
     */
    public List<AttendStatistics> selectAttendStatisticsList(AttendStatistics attendStatistics);

    /**
     * 新增月度考勤统计
     * 
     * @param attendStatistics 月度考勤统计
     * @return 结果
     */
    public int insertAttendStatistics(AttendStatistics attendStatistics);

    /**
     * 修改月度考勤统计
     * 
     * @param attendStatistics 月度考勤统计
     * @return 结果
     */
    public int updateAttendStatistics(AttendStatistics attendStatistics);

    /**
     * 删除月度考勤统计
     * 
     * @param statisticsId 月度考勤统计主键
     * @return 结果
     */
    public int deleteAttendStatisticsByStatisticsId(Long statisticsId);

    /**
     * 批量删除月度考勤统计
     * 
     * @param statisticsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttendStatisticsByStatisticsIds(Long[] statisticsIds);
}
