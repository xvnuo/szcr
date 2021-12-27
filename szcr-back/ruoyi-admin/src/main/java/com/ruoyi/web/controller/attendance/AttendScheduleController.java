package com.ruoyi.web.controller.attendance;

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
import com.ruoyi.attendance.domain.AttendSchedule;
import com.ruoyi.attendance.service.IAttendScheduleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 排班Controller
 *
 * @author xvnuo
 * @date 2021-12-19
 */
@RestController
@RequestMapping("/attendance/schedule")
public class AttendScheduleController extends BaseController
{
    @Autowired
    private IAttendScheduleService attendScheduleService;

    /**
     * 查询排班列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:schedule:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttendSchedule attendSchedule)
    {
        startPage();
        List<AttendSchedule> list = attendScheduleService.selectAttendScheduleList(attendSchedule);
        return getDataTable(list);
    }

    /**
     * 导出排班列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:schedule:export')")
    @Log(title = "排班", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttendSchedule attendSchedule)
    {
        List<AttendSchedule> list = attendScheduleService.selectAttendScheduleList(attendSchedule);
        ExcelUtil<AttendSchedule> util = new ExcelUtil<AttendSchedule>(AttendSchedule.class);
        util.exportExcel(response, list, "排班数据");
    }

    /**
     * 获取排班详细信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:schedule:query')")
    @GetMapping(value = "/{scheduleId}")
    public AjaxResult getInfo(@PathVariable("scheduleId") Long scheduleId)
    {
        return AjaxResult.success(attendScheduleService.selectAttendScheduleByScheduleId(scheduleId));
    }

    /**
     * 新增排班
     */
    @PreAuthorize("@ss.hasPermi('attendance:schedule:add')")
    @Log(title = "排班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttendSchedule attendSchedule)
    {
        return toAjax(attendScheduleService.insertAttendSchedule(attendSchedule));
    }

    /**
     * 修改排班
     */
    @PreAuthorize("@ss.hasPermi('attendance:schedule:edit')")
    @Log(title = "排班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttendSchedule attendSchedule)
    {
        return toAjax(attendScheduleService.updateAttendSchedule(attendSchedule));
    }

    /**
     * 删除排班
     */
    @PreAuthorize("@ss.hasPermi('attendance:schedule:remove')")
    @Log(title = "排班", businessType = BusinessType.DELETE)
	@DeleteMapping("/{scheduleIds}")
    public AjaxResult remove(@PathVariable Long[] scheduleIds)
    {
        return toAjax(attendScheduleService.deleteAttendScheduleByScheduleIds(scheduleIds));
    }
}
