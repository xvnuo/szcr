package com.ruoyi.attendance.service.impl;

import java.util.LinkedList;
import java.util.List;

import com.ruoyi.attendance.domain.AttendRecord;
import com.ruoyi.attendance.mapper.AttendRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.attendance.mapper.AttendStatisticsMapper;
import com.ruoyi.attendance.domain.AttendStatistics;
import com.ruoyi.attendance.service.IAttendStatisticsService;

/**
 * 考勤统计Service业务层处理
 *
 * @author xvnuo
 * @date 2022-01-12
 */
@Service
public class AttendStatisticsServiceImpl implements IAttendStatisticsService
{
    @Autowired
    private AttendStatisticsMapper attendStatisticsMapper;

    @Autowired
    private AttendRecordMapper attendRecordMapper;

    /**
     * 查询考勤统计
     *
     * @param statisticsId 考勤统计主键
     * @return 考勤统计
     */
    @Override
    public AttendStatistics selectAttendStatisticsByStatisticsId(Long statisticsId)
    {
        return attendStatisticsMapper.selectAttendStatisticsByStatisticsId(statisticsId);
    }

    /**
     * 查询考勤统计列表
     *
     * @param attendStatistics 考勤统计
     * @return 考勤统计
     */
    @Override
    public List<AttendStatistics> selectAttendStatisticsList(AttendStatistics attendStatistics)
    {
        List<AttendStatistics> stsList = new LinkedList<>();
        return stsList;
    }

    /**
     * 新增考勤统计
     *
     * @param attendStatistics 考勤统计
     * @return 结果
     */
    @Override
    public int insertAttendStatistics(AttendStatistics attendStatistics)
    {
        return attendStatisticsMapper.insertAttendStatistics(attendStatistics);
    }

    /**
     * 修改考勤统计
     *
     * @param attendStatistics 考勤统计
     * @return 结果
     */
    @Override
    public int updateAttendStatistics(AttendStatistics attendStatistics)
    {
        return attendStatisticsMapper.updateAttendStatistics(attendStatistics);
    }

    /**
     * 批量删除考勤统计
     *
     * @param statisticsIds 需要删除的考勤统计主键
     * @return 结果
     */
    @Override
    public int deleteAttendStatisticsByStatisticsIds(Long[] statisticsIds)
    {
        return attendStatisticsMapper.deleteAttendStatisticsByStatisticsIds(statisticsIds);
    }

    /**
     * 删除考勤统计信息
     *
     * @param statisticsId 考勤统计主键
     * @return 结果
     */
    @Override
    public int deleteAttendStatisticsByStatisticsId(Long statisticsId)
    {
        return attendStatisticsMapper.deleteAttendStatisticsByStatisticsId(statisticsId);
    }
}
