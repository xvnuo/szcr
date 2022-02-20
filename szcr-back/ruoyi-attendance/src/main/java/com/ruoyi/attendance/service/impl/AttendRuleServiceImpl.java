package com.ruoyi.attendance.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.domain.entity.AttendRule;
import com.ruoyi.common.core.domain.entity.AttendSchedule;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysConfigService;
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

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysConfigService configService;

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

    /**
     * 批量分配规则给用户
     *
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int cancelUsersRule(Long[] userIds) {
        return userMapper.updateUsersRule(0L, userIds);
    }

    /**
     * 批量选择分配规则给用户
     *
     * @param ruleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    @Override
    public int insertUsersRule(Long ruleId, Long[] userIds) {
        return userMapper.updateUsersRule(ruleId, userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, String operName, Long ruleId, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) // 该用户不存在，需要创建新用户，然后添加关联
                {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    user.setRuleId(ruleId);
                    userMapper.insertUser(user);
                }
                else // 允许更新已经分配规则的用户的考勤规则
                {
                    user.setUpdateBy(operName);
                    user.setRuleId(ruleId);
                    userMapper.updateUser(user);
                }
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 分配成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 分配失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入并分配成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
