package com.ruoyi.attendance.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.dingtalk.api.response.OapiAttendanceListResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.ruoyi.attendance.util.DingDingUtil;
import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.collections4.ListUtils;
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

    /**
     * 获取钉钉考勤记录
     */
    public List<AttendInitial> getDingRecordList()
    {
        // 获取服务端接口调用凭证access_token
        String access_token = DingDingUtil.getToken();
        System.out.println(access_token);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//创建日期转换对象hh:mm:ss为时分秒，年月日为yyyy-MM-dd

        Date nowDate = new Date();
        Date workDate = DateUtils.addDays(nowDate, -7);
        String endWorkDate = df.format(nowDate);
        String startWorkDate = df.format(workDate);
        System.out.println("开始统计时间" + startWorkDate);
        System.out.println("结束统计时间" + endWorkDate);

        List<OapiAttendanceListResponse.Recordresult> attendanceList = new ArrayList<>();
        List<AttendInitial> initialList = new LinkedList<>();

        // 获取全部部门列表
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> departmentList = DingDingUtil.getDepartment();
        System.out.println("部门数量" + departmentList.size());
        if(departmentList != null && departmentList.size() > 0) {
            List<String> userIdList = new ArrayList<>();
            // 获取全部用户：遍历每个部门，获取该部门内所有用户，放在总用户列表中
            for(OapiV2DepartmentListsubResponse.DeptBaseResponse department:departmentList) {
                System.out.println("部门名称" + department.getName());
                List<String> userIdListTmp = DingDingUtil.getDepartmentUserId(department.getDeptId());
                if(userIdListTmp != null && userIdListTmp.size() > 0) {
                    userIdList.addAll(userIdListTmp);
                }
            }
            // 遍历每个用户，获取每个用户的考勤记录
            if(userIdList != null && userIdList.size() > 0) {
                List<List<String>> userIds = ListUtils.partition(userIdList, 50);
                for(List<String> users:userIds) {
                    int count = 1;
                    long offset = 0L;
                    long limit = 50L;
                    List<OapiAttendanceListResponse.Recordresult> attendanceListTmp = DingDingUtil.getAttendanceList(startWorkDate, endWorkDate, users, offset, limit);
                    if(attendanceListTmp != null && attendanceListTmp.size() > 0) {
                        attendanceList.addAll(attendanceListTmp);
                        while (attendanceListTmp.size() <= 50) {
                            count++;
                            offset = (count - 1) * limit;
                            attendanceListTmp = DingDingUtil.getAttendanceList(startWorkDate, endWorkDate, users, offset, limit);
                            if(attendanceListTmp == null || attendanceListTmp.size() == 0) {
                                break;
                            }
                            attendanceList.addAll(attendanceListTmp);
                        }
                    }
                }
            }
            if(attendanceList != null && attendanceList.size() > 0) {
                for(OapiAttendanceListResponse.Recordresult attendance: attendanceList) {
                    if(!attendance.getSourceType().equals("SYSTEM")){// 只保存用户和管理员创建的打卡记录
                        AttendInitial record = new AttendInitial();
                        record.setUserId(Long.parseLong(attendance.getUserId().substring(7)));
                        record.setInitialPlace(attendance.getLocationResult());
                        record.setInitialTime(attendance.getUserCheckTime());
                        System.out.println(df.format(attendance.getUserCheckTime()));
                        record.setUpdateBy("钉钉");
                        record.setUpdateTime(nowDate);
                        record.setCreateBy("钉钉");
                        record.setCreateTime(nowDate);
                        initialList.add(record);
                        System.out.println(attendance.getUserId());
                        //attendInitialMapper.insertAttendInitial(record);
                    }
                }
            }
        }
        return initialList;
    }
}
















