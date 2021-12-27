package com.ruoyi.attendance.service;

import java.util.List;
import com.ruoyi.attendance.domain.AttendRule;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 考勤规则Service接口
 *
 * @author xvnuo
 * @date 2021-12-19
 */
public interface IAttendRuleService
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
     * 修改规则状态
     *
     * @param rule 考勤规则
     * @return 结果
     */
    public int updateRuleStatus(AttendRule rule);

    /**
     * 修改考勤规则
     *
     * @param attendRule 考勤规则
     * @return 结果
     */
    public int updateAttendRule(AttendRule attendRule);

    /**
     * 批量删除考勤规则
     *
     * @param ruleIds 需要删除的考勤规则主键集合
     * @return 结果
     */
    public int deleteAttendRuleByRuleIds(Long[] ruleIds);

    /**
     * 删除考勤规则信息
     *
     * @param ruleId 考勤规则主键
     * @return 结果
     */
    public int deleteAttendRuleByRuleId(Long ruleId);
}
