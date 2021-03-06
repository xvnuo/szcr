package com.ruoyi.attendance.service;

import java.util.List;
import com.ruoyi.attendance.domain.AttendInitial;

/**
 * 原始考勤记录Service接口
 *
 * @author xvnuo
 * @date 2022-02-06
 */
public interface IAttendInitialService
{
    /**
     * 查询原始考勤记录
     *
     * @param initialId 原始考勤记录主键
     * @return 原始考勤记录
     */
    public AttendInitial selectAttendInitialByInitialId(Long initialId);

    /**
     * 查询原始考勤记录列表
     *
     * @param attendInitial 原始考勤记录
     * @return 原始考勤记录集合
     */
    public List<AttendInitial> selectAttendInitialList(AttendInitial attendInitial);

    /**
     * 新增原始考勤记录
     *
     * @param attendInitial 原始考勤记录
     * @return 结果
     */
    public int insertAttendInitial(AttendInitial attendInitial);

    /**
     * 修改原始考勤记录
     *
     * @param attendInitial 原始考勤记录
     * @return 结果
     */
    public int updateAttendInitial(AttendInitial attendInitial);

    /**
     * 批量删除原始考勤记录
     *
     * @param initialIds 需要删除的原始考勤记录主键集合
     * @return 结果
     */
    public int deleteAttendInitialByInitialIds(Long[] initialIds);

    /**
     * 删除原始考勤记录信息
     *
     * @param initialId 原始考勤记录主键
     * @return 结果
     */
    public int deleteAttendInitialByInitialId(Long initialId);

    /**
     * 获取钉钉考勤记录
     */
    public List<AttendInitial> getDingRecordList();

}
