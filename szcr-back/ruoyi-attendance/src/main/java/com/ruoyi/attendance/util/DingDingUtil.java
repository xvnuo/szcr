package com.ruoyi.attendance.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 钉钉工具类
 */
@Slf4j
public class DingDingUtil {

    private final static String SYSTEM_ERROR ="SYSTEM_ERROR";
    public static String AppKey = "dinguehyjbmfgcpa9p4t";
    public static String AppSecret = "0S_mvtzmqFFCHFEJ1ax4-EKyun6GCKdKa2eEF1E4DzuHOolVC0grKZby2PNqgxYj";

    //获取token
    public static String getToken() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(AppKey);
            request.setAppsecret(AppSecret);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            throw new RuntimeException();
        }

    }

    //获取部门列表
    public static List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDepartment(){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
        OapiV2DepartmentListsubRequest request = new OapiV2DepartmentListsubRequest();
        request.setHttpMethod("GET");
        try {
            OapiV2DepartmentListsubResponse response = client.execute(request, DingDingUtil.getToken());
            if(!response.isSuccess()) {
                log.error("调用钉钉接口失败："+response.getMessage());
            }
            return response.isSuccess() ? response.getResult():null;
        } catch (ApiException e) {
            log.error(DingDingUtil.SYSTEM_ERROR, e);
        }
        return null;
    }

    //获取部门下的所有用户列表
    public static List<String> getDepartmentUserId(Long departmentId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listid");
        OapiUserListidRequest req = new OapiUserListidRequest();
        req.setDeptId(departmentId);

        try {
            OapiUserListidResponse response = client.execute(req, DingDingUtil.getToken());
            if(!response.isSuccess()) {
                log.error("调用钉钉接口失败："+response.getMessage());
            }
            return response.isSuccess()?response.getResult().getUseridList():null;
        } catch (ApiException e) {
            log.error(DingDingUtil.SYSTEM_ERROR, e);
        }
        return null;
    }

    //获取部门下的所有用户列表
    public static OapiV2UserListResponse.PageResult getDepartmentUser(Long departmentId, long cursor, long size){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
        OapiV2UserListRequest request = new OapiV2UserListRequest();
        request.setDeptId(departmentId);
        request.setCursor(cursor);
        request.setSize(size);
        request.setOrderField("modify_desc");
        request.setHttpMethod("GET");
        try {
            OapiV2UserListResponse  response = client.execute(request, DingDingUtil.getToken());
            if(!response.isSuccess()) {
                log.error("调用钉钉接口失败："+response.getMessage());
            }
            return response.isSuccess()?response.getResult():null;
        } catch (ApiException e) {
            log.error(DingDingUtil.SYSTEM_ERROR, e);
        }
        return null;
    }

    //获取钉钉考勤记录
    public static List<OapiAttendanceListResponse.Recordresult> getAttendanceList(String startWorkDate, String endWorkDate, List<String> userIdList, long offset, long limit) {
        // 通过调用接口获取考勤打卡结果
        DingTalkClient clientDingTalkClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/list");
        OapiAttendanceListRequest requestAttendanceListRequest = new OapiAttendanceListRequest();
        // 查询考勤打卡记录的起始工作日
        requestAttendanceListRequest.setWorkDateFrom(startWorkDate);
        // 查询考勤打卡记录的结束工作日
        requestAttendanceListRequest.setWorkDateTo(endWorkDate);
        // 员工在企业内的userid列表，最多不能超过50个。
        requestAttendanceListRequest.setUserIdList(userIdList);
        // 表示获取考勤数据的起始点
        requestAttendanceListRequest.setOffset(offset);
        // 表示获取考勤数据的条数，最大不能超过50条。
        requestAttendanceListRequest.setLimit(limit);
        OapiAttendanceListResponse response = null;
        try {
            response = clientDingTalkClient.execute(requestAttendanceListRequest,DingDingUtil.getToken());
            if(!response.isSuccess()) {
                log.error("调用钉钉接口失败："+response.getMessage());
            }
            return response.isSuccess()?response.getRecordresult():null;
        } catch (ApiException e) {
            log.error(DingDingUtil.SYSTEM_ERROR, e);
        }
        return null;
    }
}
