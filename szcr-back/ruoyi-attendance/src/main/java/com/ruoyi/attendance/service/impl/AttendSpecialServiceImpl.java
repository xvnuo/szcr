package com.ruoyi.attendance.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.attendance.mapper.AttendSpecialMapper;
import com.ruoyi.attendance.domain.AttendSpecial;
import com.ruoyi.attendance.service.IAttendSpecialService;

/**
 * 异常考勤Service业务层处理
 * 
 * @author xvnuo
 * @date 2022-01-23
 */
@Service
public class AttendSpecialServiceImpl implements IAttendSpecialService 
{
    @Autowired
    private AttendSpecialMapper attendSpecialMapper;

    /**
     * 查询异常考勤
     * 
     * @param specialId 异常考勤主键
     * @return 异常考勤
     */
    @Override
    public AttendSpecial selectAttendSpecialBySpecialId(Long specialId)
    {
        return attendSpecialMapper.selectAttendSpecialBySpecialId(specialId);
    }

    /**
     * 查询异常考勤列表
     * 
     * @param attendSpecial 异常考勤
     * @return 异常考勤
     */
    @Override
    public List<AttendSpecial> selectAttendSpecialList(AttendSpecial attendSpecial)
    {
        return attendSpecialMapper.selectAttendSpecialList(attendSpecial);
    }

    /**
     * 新增异常考勤
     * 
     * @param attendSpecial 异常考勤
     * @return 结果
     */
    @Override
    public int insertAttendSpecial(AttendSpecial attendSpecial)
    {
        attendSpecial.setCreateTime(DateUtils.getNowDate());
        return attendSpecialMapper.insertAttendSpecial(attendSpecial);
    }

    /**
     * 修改异常考勤
     * 
     * @param attendSpecial 异常考勤
     * @return 结果
     */
    @Override
    public int updateAttendSpecial(AttendSpecial attendSpecial)
    {
        attendSpecial.setUpdateTime(DateUtils.getNowDate());
        return attendSpecialMapper.updateAttendSpecial(attendSpecial);
    }

    /**
     * 批量删除异常考勤
     * 
     * @param specialIds 需要删除的异常考勤主键
     * @return 结果
     */
    @Override
    public int deleteAttendSpecialBySpecialIds(Long[] specialIds)
    {
        return attendSpecialMapper.deleteAttendSpecialBySpecialIds(specialIds);
    }

    /**
     * 删除异常考勤信息
     * 
     * @param specialId 异常考勤主键
     * @return 结果
     */
    @Override
    public int deleteAttendSpecialBySpecialId(Long specialId)
    {
        return attendSpecialMapper.deleteAttendSpecialBySpecialId(specialId);
    }
}
