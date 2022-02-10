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
        Date date = new Date();
        List<SysUser> userList = userMapper.selectUserList(new SysUser());
        for(SysUser user: userList){
            System.out.println(user.getUserName());
            List<AttendRecord> recordList = initialMapper.selectAttendRecordList(user.getUserId(), date);
            System.out.println(recordList.size());
        }
        return attendRecordMapper.selectAttendRecordList(attendRecord);
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
