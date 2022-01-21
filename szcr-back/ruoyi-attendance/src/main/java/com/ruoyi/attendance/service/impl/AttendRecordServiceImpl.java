package com.ruoyi.attendance.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.attendance.mapper.AttendRuleMapper;
import com.ruoyi.common.core.domain.entity.AttendRule;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.attendance.mapper.AttendRecordMapper;
import com.ruoyi.attendance.domain.AttendRecord;
import com.ruoyi.attendance.service.IAttendRecordService;

/**
 * 考勤记录Service业务层处理
 *
 * @author xvnuo
 * @date 2022-01-20
 */
@Service
public class AttendRecordServiceImpl implements IAttendRecordService
{
    @Autowired
    private AttendRecordMapper attendRecordMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private AttendRuleMapper ruleMapper;

    /**
     * 查询考勤记录
     *
     * @param recordId 考勤记录主键
     * @return 考勤记录
     */
    @Override
    public AttendRecord selectAttendRecordByRecordId(Long recordId)
    {
        return attendRecordMapper.selectAttendRecordByRecordId(recordId);
    }

    /**
     * 查询考勤记录列表
     *
     * @param attendRecord 考勤记录
     * @return 考勤记录
     */
    @Override
    public List<AttendRecord> selectAttendRecordList(AttendRecord attendRecord)
    {
        return attendRecordMapper.selectAttendRecordList(attendRecord);
    }

    /**
     * 新增考勤记录
     *
     * @param newRecord 考勤记录
     * @return 结果
     */
    @Override
    public int insertAttendRecord(AttendRecord newRecord)
    {
        /*
        AttendRecord oldRecord = attendRecordMapper.selectByUserIdAndDate(newRecord.getUserId(), newRecord.getAttendDate());
        if(oldRecord==null){// 数据库中当日该用户暂无考勤记录
            setRecordStatus(newRecord);
            newRecord.setCreateTime(DateUtils.getNowDate());
            return attendRecordMapper.insertAttendRecord(newRecord);
        }
        else{
            if(newRecord.getOnTime().getTime()<oldRecord.getOnTime().getTime()){
            }
            if(newRecord.getOffTime().getTime()>oldRecord.getOffTime().getTime()){
            }
            setRecordStatus(oldRecord);
            oldRecord.setUpdateTime(DateUtils.getNowDate());
            return attendRecordMapper.updateAttendRecord(oldRecord);
        }
*/
        newRecord.setCreateTime(DateUtils.getNowDate());
        return attendRecordMapper.insertAttendRecord(newRecord);
    }

    /**
     * 设置考勤记录状态
     * @param record 记录
     */
    public void setRecordStatus(AttendRecord record){
        // 查找该用户
        SysUser user = userMapper.selectUserById(record.getUserId());
        // 查找该用户的考勤规则
        AttendRule rule = ruleMapper.selectAttendRuleByRuleId(user.getRuleId());
        switch (rule.getRuleType()) {
            case "1": // 固定考勤

                break;
            case "2": // 弹性考勤

                break;
            case "3": // 排班考勤

                break;
        }

    }

    /**
     * 修改考勤记录
     *
     * @param attendRecord 考勤记录
     * @return 结果
     */
    @Override
    public int updateAttendRecord(AttendRecord attendRecord)
    {
        attendRecord.setUpdateTime(DateUtils.getNowDate());
        return attendRecordMapper.updateAttendRecord(attendRecord);
    }

    /**
     * 批量删除考勤记录
     *
     * @param recordIds 需要删除的考勤记录主键
     * @return 结果
     */
    @Override
    public int deleteAttendRecordByRecordIds(Long[] recordIds)
    {
        return attendRecordMapper.deleteAttendRecordByRecordIds(recordIds);
    }

    /**
     * 删除考勤记录信息
     *
     * @param recordId 考勤记录主键
     * @return 结果
     */
    @Override
    public int deleteAttendRecordByRecordId(Long recordId)
    {
        return attendRecordMapper.deleteAttendRecordByRecordId(recordId);
    }
}
