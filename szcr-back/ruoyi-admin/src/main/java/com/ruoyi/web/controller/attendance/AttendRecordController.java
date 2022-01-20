package com.ruoyi.attendance.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.attendance.domain.AttendRecord;
import com.ruoyi.attendance.service.IAttendRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考勤记录Controller
 * 
 * @author xvnuo
 * @date 2022-01-20
 */
@RestController
@RequestMapping("/attendance/record")
public class AttendRecordController extends BaseController
{
    @Autowired
    private IAttendRecordService attendRecordService;

    /**
     * 查询考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttendRecord attendRecord)
    {
        startPage();
        List<AttendRecord> list = attendRecordService.selectAttendRecordList(attendRecord);
        return getDataTable(list);
    }

    /**
     * 导出考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:record:export')")
    @Log(title = "考勤记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttendRecord attendRecord)
    {
        List<AttendRecord> list = attendRecordService.selectAttendRecordList(attendRecord);
        ExcelUtil<AttendRecord> util = new ExcelUtil<AttendRecord>(AttendRecord.class);
        util.exportExcel(response, list, "考勤记录数据");
    }

    /**
     * 获取考勤记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:record:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(attendRecordService.selectAttendRecordByRecordId(recordId));
    }

    /**
     * 新增考勤记录
     */
    @PreAuthorize("@ss.hasPermi('attendance:record:add')")
    @Log(title = "考勤记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttendRecord attendRecord)
    {
        return toAjax(attendRecordService.insertAttendRecord(attendRecord));
    }

    /**
     * 修改考勤记录
     */
    @PreAuthorize("@ss.hasPermi('attendance:record:edit')")
    @Log(title = "考勤记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttendRecord attendRecord)
    {
        return toAjax(attendRecordService.updateAttendRecord(attendRecord));
    }

    /**
     * 删除考勤记录
     */
    @PreAuthorize("@ss.hasPermi('attendance:record:remove')")
    @Log(title = "考勤记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(attendRecordService.deleteAttendRecordByRecordIds(recordIds));
    }
}
