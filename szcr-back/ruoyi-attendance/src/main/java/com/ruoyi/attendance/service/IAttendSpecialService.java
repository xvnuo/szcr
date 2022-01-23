package com.ruoyi.attendance.service;

import java.util.List;
import com.ruoyi.attendance.domain.AttendSpecial;

/**
 * 异常考勤Service接口
 * 
 * @author xvnuo
 * @date 2022-01-23
 */
public interface IAttendSpecialService 
{
    /**
     * 查询异常考勤
     * 
     * @param specialId 异常考勤主键
     * @return 异常考勤
     */
    public AttendSpecial selectAttendSpecialBySpecialId(Long specialId);

    /**
     * 查询异常考勤列表
     * 
     * @param attendSpecial 异常考勤
     * @return 异常考勤集合
     */
    public List<AttendSpecial> selectAttendSpecialList(AttendSpecial attendSpecial);

    /**
     * 新增异常考勤
     * 
     * @param attendSpecial 异常考勤
     * @return 结果
     */
    public int insertAttendSpecial(AttendSpecial attendSpecial);

    /**
     * 修改异常考勤
     * 
     * @param attendSpecial 异常考勤
     * @return 结果
     */
    public int updateAttendSpecial(AttendSpecial attendSpecial);

    /**
     * 批量删除异常考勤
     * 
     * @param specialIds 需要删除的异常考勤主键集合
     * @return 结果
     */
    public int deleteAttendSpecialBySpecialIds(Long[] specialIds);

    /**
     * 删除异常考勤信息
     * 
     * @param specialId 异常考勤主键
     * @return 结果
     */
    public int deleteAttendSpecialBySpecialId(Long specialId);
}
