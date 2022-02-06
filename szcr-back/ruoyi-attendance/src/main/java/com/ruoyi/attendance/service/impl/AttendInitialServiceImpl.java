package com.ruoyi.attendance.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.attendance.mapper.AttendInitialMapper;
import com.ruoyi.attendance.domain.AttendInitial;
import com.ruoyi.attendance.service.IAttendInitialService;

/**
 * 原始考勤记录Service业务层处理
 * 
 * @author xvnuo
 * @date 2022-02-06
 */
@Service
public class AttendInitialServiceImpl implements IAttendInitialService 
{
    @Autowired
    private AttendInitialMapper attendInitialMapper;

    /**
     * 查询原始考勤记录
     * 
     * @param initialId 原始考勤记录主键
     * @return 原始考勤记录
     */
    @Override
    public AttendInitial selectAttendInitialByInitialId(Long initialId)
    {
        return attendInitialMapper.selectAttendInitialByInitialId(initialId);
    }

    /**
     * 查询原始考勤记录列表
     * 
     * @param attendInitial 原始考勤记录
     * @return 原始考勤记录
     */
    @Override
    public List<AttendInitial> selectAttendInitialList(AttendInitial attendInitial)
    {
        return attendInitialMapper.selectAttendInitialList(attendInitial);
    }

    /**
     * 新增原始考勤记录
     * 
     * @param attendInitial 原始考勤记录
     * @return 结果
     */
    @Override
    public int insertAttendInitial(AttendInitial attendInitial)
    {
        return attendInitialMapper.insertAttendInitial(attendInitial);
    }

    /**
     * 修改原始考勤记录
     * 
     * @param attendInitial 原始考勤记录
     * @return 结果
     */
    @Override
    public int updateAttendInitial(AttendInitial attendInitial)
    {
        return attendInitialMapper.updateAttendInitial(attendInitial);
    }

    /**
     * 批量删除原始考勤记录
     * 
     * @param initialIds 需要删除的原始考勤记录主键
     * @return 结果
     */
    @Override
    public int deleteAttendInitialByInitialIds(Long[] initialIds)
    {
        return attendInitialMapper.deleteAttendInitialByInitialIds(initialIds);
    }

    /**
     * 删除原始考勤记录信息
     * 
     * @param initialId 原始考勤记录主键
     * @return 结果
     */
    @Override
    public int deleteAttendInitialByInitialId(Long initialId)
    {
        return attendInitialMapper.deleteAttendInitialByInitialId(initialId);
    }
}
