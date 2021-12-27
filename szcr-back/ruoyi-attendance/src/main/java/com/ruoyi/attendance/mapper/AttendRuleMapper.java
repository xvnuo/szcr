package com.ruoyi.attendance.mapper;

import java.util.List;
import com.ruoyi.attendance.domain.AttendRule;
import com.ruoyi.attendance.domain.AttendSchedule;

/**
 * 考勤规则Mapper接口
 * 
 * @author xvnuo
 * @date 2021-12-19
 */
public interface AttendRuleMapper 
{
    /**
     * 查询考勤规则
     * 
     * @param ruleId 考勤规则主键
     * @return 考勤规则
     */
    public AttendRule selectAttendRuleByRuleId(Long ruleId);

    /**
     * 查询考勤规则列表
     * 
     * @param attendRule 考勤规则
     * @return 考勤规则集合
     */
    public List<AttendRule> selectAttendRuleList(AttendRule attendRule);

    /**
     * 新增考勤规则
     * 
     * @param attendRule 考勤规则
     * @return 结果
     */
    public int insertAttendRule(AttendRule attendRule);

    /**
     * 修改考勤规则
     * 
     * @param attendRule 考勤规则
     * @return 结果
     */
    public int updateAttendRule(AttendRule attendRule);

    /**
     * 删除考勤规则
     * 
     * @param ruleId 考勤规则主键
     * @return 结果
     */
    public int deleteAttendRuleByRuleId(Long ruleId);

    /**
     * 批量删除考勤规则
     * 
     * @param ruleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttendRuleByRuleIds(Long[] ruleIds);

    /**
     * 批量删除排班
     * 
     * @param ruleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttendScheduleByRuleIds(Long[] ruleIds);
    
    /**
     * 批量新增排班
     * 
     * @param attendScheduleList 排班列表
     * @return 结果
     */
    public int batchAttendSchedule(List<AttendSchedule> attendScheduleList);
    

    /**
     * 通过考勤规则主键删除排班信息
     * 
     * @param ruleId 考勤规则ID
     * @return 结果
     */
    public int deleteAttendScheduleByRuleId(Long ruleId);
}
