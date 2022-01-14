package com.ruoyi.attendance.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.domain.entity.AttendRule;
import com.ruoyi.common.core.domain.entity.AttendSchedule;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.attendance.mapper.AttendRuleMapper;
import com.ruoyi.attendance.service.IAttendRuleService;

/**
 * 考勤规则Service业务层处理
 *
 * @author xvnuo
 * @date 2021-12-19
 */
@Service
public class AttendRuleServiceImpl implements IAttendRuleService
{
    @Autowired
    private AttendRuleMapper attendRuleMapper;

    /**
     * 查询考勤规则
     *
     * @param ruleId 考勤规则主键
     * @return 考勤规则
     */
    @Override
    public AttendRule selectAttendRuleByRuleId(Long ruleId)
    {
        return attendRuleMapper.selectAttendRuleByRuleId(ruleId);
    }

    /**
     * 查询考勤规则列表
     *
     * @param attendRule 考勤规则
     * @return 考勤规则
     */
    @Override
    public List<AttendRule> selectAttendRuleList(AttendRule attendRule)
    {
        return attendRuleMapper.selectAttendRuleList(attendRule);
    }

    /**
     * 新增考勤规则
     *
     * @param attendRule 考勤规则
     * @return 结果
     */
    @Transactional
    @Override
    public int insertAttendRule(AttendRule attendRule)
    {
        attendRule.setCreateTime(DateUtils.getNowDate());
        int rows = attendRuleMapper.insertAttendRule(attendRule);
        insertAttendSchedule(attendRule);
        return rows;
    }

    /**
     * 修改考勤状态
     *
     * @param rule 考勤规则
     * @return 结果
     */
    @Override
    public int updateRuleStatus(AttendRule rule)
    {
        return attendRuleMapper.updateAttendRule(rule);
    }

    /**
     * 修改考勤规则
     *
     * @param attendRule 考勤规则
     * @return 结果
     */
    @Transactional
    @Override
    public int updateAttendRule(AttendRule attendRule)
    {
        attendRule.setUpdateTime(DateUtils.getNowDate());
        attendRuleMapper.deleteAttendScheduleByRuleId(attendRule.getRuleId());
        insertAttendSchedule(attendRule);
        return attendRuleMapper.updateAttendRule(attendRule);
    }

    /**
     * 批量删除考勤规则
     *
     * @param ruleIds 需要删除的考勤规则主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteAttendRuleByRuleIds(Long[] ruleIds)
    {
        attendRuleMapper.deleteAttendScheduleByRuleIds(ruleIds);
        return attendRuleMapper.deleteAttendRuleByRuleIds(ruleIds);
    }

    /**
     * 删除考勤规则信息
     *
     * @param ruleId 考勤规则主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteAttendRuleByRuleId(Long ruleId)
    {
        attendRuleMapper.deleteAttendScheduleByRuleId(ruleId);
        return attendRuleMapper.deleteAttendRuleByRuleId(ruleId);
    }

    /**
     * 新增排班信息
     *
     * @param attendRule 考勤规则对象
     */
    public void insertAttendSchedule(AttendRule attendRule)
    {
        List<AttendSchedule> attendScheduleList = attendRule.getAttendScheduleList();
        Long ruleId = attendRule.getRuleId();
        if (StringUtils.isNotNull(attendScheduleList))
        {
            List<AttendSchedule> list = new ArrayList<AttendSchedule>();
            for (AttendSchedule attendSchedule : attendScheduleList)
            {
                attendSchedule.setRuleId(ruleId);
                list.add(attendSchedule);
            }
            if (list.size() > 0)
            {
                attendRuleMapper.batchAttendSchedule(list);
            }
        }
    }
}
