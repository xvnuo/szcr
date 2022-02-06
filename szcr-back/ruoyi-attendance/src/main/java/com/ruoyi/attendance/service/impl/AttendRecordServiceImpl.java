package com.ruoyi.attendance.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.attendance.domain.AttendStatistics;
import com.ruoyi.attendance.mapper.AttendInitialMapper;
import com.ruoyi.attendance.mapper.AttendRuleMapper;
import com.ruoyi.common.core.domain.entity.AttendRule;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.attendance.mapper.AttendRecordMapper;
import com.ruoyi.attendance.domain.AttendRecord;
import com.ruoyi.attendance.service.IAttendRecordService;

/**
 * 考勤记录Service业务层处理
 *
 * @author xvnuo
 * @date 2022-01-23
 */
@Service
public class AttendRecordServiceImpl implements IAttendRecordService
{
    @Autowired
    private AttendRecordMapper attendRecordMapper;

    @Autowired
    private AttendInitialMapper initialMapper;

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
        if(attendRecord!=null){
            return attendRecordMapper.selectAttendRecordList(attendRecord);
        }
        List<AttendRecord> list1 = initialMapper.selectAttendRecordList(attendRecord);
        Map<Long, AttendRule> userRuleMap = new HashMap<>();
        for(AttendRecord record: list1){
            // 寻找用户对应的考勤规则
            AttendRule rule = userRuleMap.getOrDefault(record.getUserId(), null);
            if(rule==null){
                rule = ruleMapper.selectAttendRuleByRuleId(record.getRuleId());
                userRuleMap.put(record.getUserId(), rule);
            }
            Double diff = (record.getOffTime().getTime() - record.getOnTime().getTime())/(1.0*1000*60*60);
            record.setAttendHour(diff);
            // 1-正常，2-迟到，3-早退，4-缺勤，5-请假
            // 弹性考勤-2
            if(rule.getRuleType().equals("2")){
                // 弹性考勤的上班状态都是正常的
                record.setOnStatus("1");
                // 若工时小于弹性规则最短工时，判定为早退
                record.setOffStatus(diff<rule.getWorkHour()? "3": "1");
            }
            // 固定考勤-1
            else if(rule.getRuleType().equals("1")){
                // 判定上下班状态
                record.setOnStatus(record.getOnTime().compareTo(rule.getOnTime())<0? "1":"2");
                record.setOffStatus(record.getOffTime().compareTo(rule.getOffTime())>0? "1": "3");
            }
            // 排班考勤-3
            else {
                // to do
            }
            List<AttendRecord> tmpList = attendRecordMapper.selectAttendRecordList(record);
            if(tmpList==null || tmpList.size()==0){
                insertAttendRecord(record);
            }
            else{
                updateAttendRecord(record);
            }
        }
        return attendRecordMapper.selectAttendRecordList(null);
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
