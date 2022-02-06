package com.ruoyi.attendance.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.ruoyi.attendance.mapper.AttendRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.attendance.mapper.AttendStatisticsMapper;
import com.ruoyi.attendance.domain.AttendStatistics;
import com.ruoyi.attendance.service.IAttendStatisticsService;

/**
 * 月度考勤统计Service业务层处理
 *
 * @author xvnuo
 * @date 2022-01-23
 */
@Service
public class AttendStatisticsServiceImpl implements IAttendStatisticsService
{
    @Autowired
    private AttendStatisticsMapper attendStatisticsMapper;

    @Autowired
    private AttendRecordMapper recordMapper;

    /**
     * 查询月度考勤统计
     *
     * @param statisticsId 月度考勤统计主键
     * @return 月度考勤统计
     */
    @Override
    public AttendStatistics selectAttendStatisticsByStatisticsId(Long statisticsId)
    {
        return attendStatisticsMapper.selectAttendStatisticsByStatisticsId(statisticsId);
    }

    /**
     * 查询月度考勤统计列表
     *
     * @param attendStatistics 月度考勤统计
     * @return 月度考勤统计
     */
    @Override
    public List<AttendStatistics> selectAttendStatisticsList(AttendStatistics attendStatistics)
    {
        // 如果未初始化年-月，则将其设为系统当前的年份-月份
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM");
        if(attendStatistics.getAttendMonth()==null){
            System.out.println(ft.format(date));
            attendStatistics.setAttendMonth(ft.format(date));
        }
        List<AttendStatistics> resList = new LinkedList<>();
        resList.addAll(recordMapper.generateStatisticsList(attendStatistics));
        for(AttendStatistics statistics: resList){
            List<AttendStatistics> tmpList = attendStatisticsMapper.selectAttendStatisticsList(statistics);
            if(tmpList==null || tmpList.size()==0){
                attendStatisticsMapper.insertAttendStatistics(statistics);
            }
            else{
                attendStatisticsMapper.updateAttendStatistics(statistics);
            }
        }
        return attendStatisticsMapper.selectAttendStatisticsList(attendStatistics);
    }

    /**
     * 新增月度考勤统计
     *
     * @param attendStatistics 月度考勤统计
     * @return 结果
     */
    @Override
    public int insertAttendStatistics(AttendStatistics attendStatistics)
    {
        return attendStatisticsMapper.insertAttendStatistics(attendStatistics);
    }

    /**
     * 修改月度考勤统计
     *
     * @param attendStatistics 月度考勤统计
     * @return 结果
     */
    @Override
    public int updateAttendStatistics(AttendStatistics attendStatistics)
    {
        return attendStatisticsMapper.updateAttendStatistics(attendStatistics);
    }

    /**
     * 批量删除月度考勤统计
     *
     * @param statisticsIds 需要删除的月度考勤统计主键
     * @return 结果
     */
    @Override
    public int deleteAttendStatisticsByStatisticsIds(Long[] statisticsIds)
    {
        return attendStatisticsMapper.deleteAttendStatisticsByStatisticsIds(statisticsIds);
    }

    /**
     * 删除月度考勤统计信息
     *
     * @param statisticsId 月度考勤统计主键
     * @return 结果
     */
    @Override
    public int deleteAttendStatisticsByStatisticsId(Long statisticsId)
    {
        return attendStatisticsMapper.deleteAttendStatisticsByStatisticsId(statisticsId);
    }
}
