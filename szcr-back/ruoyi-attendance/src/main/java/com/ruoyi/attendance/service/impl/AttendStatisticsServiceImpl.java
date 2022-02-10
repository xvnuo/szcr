package com.ruoyi.attendance.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.ruoyi.attendance.mapper.AttendRecordMapper;
import com.ruoyi.attendance.mapper.AttendRuleMapper;
import com.ruoyi.common.core.domain.entity.AttendRule;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
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

    @Autowired
    private SysUserMapper userMapper;

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
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM");
        List<SysUser> userList = userMapper.selectUserList(new SysUser());
        for(SysUser user: userList){
            List<AttendStatistics> statisticsList = recordMapper.selectStatisticsList(user.getUserId(), date);
            for(AttendStatistics statistics: statisticsList){
                AttendStatistics tmp = attendStatisticsMapper.checkDuplicate(user.getUserId(), ft.format(date));
                if(tmp==null){
                    attendStatisticsMapper.insertAttendStatistics(statistics);
                }
                else{
                    attendStatisticsMapper.updateAttendStatistics(statistics);
                }
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
