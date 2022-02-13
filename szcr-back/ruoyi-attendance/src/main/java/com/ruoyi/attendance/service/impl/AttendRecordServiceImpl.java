package com.ruoyi.attendance.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.attendance.mapper.AttendInitialMapper;
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
 * @date 2022-02-09
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

    @Autowired
    private AttendInitialMapper initialMapper;

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

    @Override
    public void updateAllAttendRecord(){
        Date date = new Date();
        List<SysUser> userList = userMapper.selectUserList(new SysUser());
        // 遍历每个用户
        for(SysUser user: userList){
            // 用户的考勤规则
            AttendRule rule = ruleMapper.selectAttendRuleByRuleId(user.getRuleId());
            // 用户至今为止的全部考勤记录
            List<AttendRecord> recordList = initialMapper.selectAttendRecordList(user.getUserId(), date);
            for(AttendRecord record: recordList){
                // 这天未出勤：缺勤或者正常休假
                if(record.getUserId()==null){
                    record.setUserId(user.getUserId());
                    record.setUserName(user.getUserName());
                    record.setRuleId(user.getRuleId());
                    record.setRuleName(rule.getRuleName());
                    // 考勤规则规定这天无需考勤或者这天是休假,attendType=2,休假
                    if(!rule.getWorkDays().contains(String.valueOf(record.getWeekdayNum())) || record.getIsWorkday().equals("3")){
                        record.setAttendType("2");
                    }
                    else{// 需要考勤，且不是休假，attendType=5，缺勤
                        record.setAttendType("5");
                    }
                }
                // 这天出勤：正常、迟到、早退、加班
                else{
                    // 这天不需要出勤或者是节假日，但是出勤了，attendType=4,加班
                    if(!rule.getWorkDays().contains(String.valueOf(record.getWeekdayNum())) || record.getIsWorkday().equals("3")){
                        record.setAttendType("4");
                    }
                    else{
                        record.setAttendType("1");// attendType=1，出勤
                    }
                    Double diff = (record.getOffTime().getTime()-record.getOnTime().getTime())/(1.0*1000*60*60);
                    record.setAttendHour(diff);
                    if(rule.getRuleType().equals("1")){// 固定考勤
                        record.setOnStatus(rule.getOnTime().getTime()>=record.getOnTime().getTime()?"1":"2");// 正常or迟到
                        record.setOffStatus(rule.getOffTime().getTime()<=record.getOffTime().getTime()?"1":"3");// 正常or早退
                    }
                    else if(rule.getRuleType().equals("2")){// 弹性考勤
                        record.setOnStatus("1");// 上班状态-正常
                        record.setOffStatus(diff>=rule.getWorkHour()?"1":"3");//下班状态-正常、早退
                    }
                }
                AttendRecord tmp = attendRecordMapper.checkDuplicate(record.getUserId(), record.getAttendDate());
                if(tmp==null){
                    System.out.println(record.getUserId());
                    attendRecordMapper.insertAttendRecord(record);
                }
                else{
                    if(!record.getAttendType().equals("3")){// 请假状态
                        attendRecordMapper.updateAttendRecord(record);
                    }
                }
            }
        }
    }


    /**
     * 新增考勤记录
     *
     * @param attendRecord 考勤记录
     * @return 结果
     */
    @Override
    public int insertAttendRecord(AttendRecord attendRecord)
    {
        attendRecord.setCreateTime(DateUtils.getNowDate());
        return attendRecordMapper.insertAttendRecord(attendRecord);
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
