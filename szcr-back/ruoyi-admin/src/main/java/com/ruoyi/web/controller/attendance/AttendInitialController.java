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
import com.ruoyi.attendance.domain.AttendInitial;
import com.ruoyi.attendance.service.IAttendInitialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 原始考勤记录Controller
 *
 * @author xvnuo
 * @date 2022-02-06
 */
@RestController
@RequestMapping("/attendance/initial")
public class AttendInitialController extends BaseController
{
    @Autowired
    private IAttendInitialService attendInitialService;

    /**
     * 查询原始考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:initial:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttendInitial attendInitial)
    {
        startPage();
        List<AttendInitial> list = attendInitialService.selectAttendInitialList(attendInitial);
        return getDataTable(list);
    }

    /**
     * 导出原始考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:initial:export')")
    @Log(title = "原始考勤记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttendInitial attendInitial)
    {
        List<AttendInitial> list = attendInitialService.selectAttendInitialList(attendInitial);
        ExcelUtil<AttendInitial> util = new ExcelUtil<AttendInitial>(AttendInitial.class);
        util.exportExcel(response, list, "原始考勤记录数据");
    }

    /**
     * 获取原始考勤记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:initial:query')")
    @GetMapping(value = "/{initialId}")
    public AjaxResult getInfo(@PathVariable("initialId") Long initialId)
    {
        return AjaxResult.success(attendInitialService.selectAttendInitialByInitialId(initialId));
    }

    /**
     * 新增原始考勤记录
     */
    @PreAuthorize("@ss.hasPermi('attendance:initial:add')")
    @Log(title = "原始考勤记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttendInitial attendInitial)
    {
        return toAjax(attendInitialService.insertAttendInitial(attendInitial));
    }

    /**
     * 修改原始考勤记录
     */
    @PreAuthorize("@ss.hasPermi('attendance:initial:edit')")
    @Log(title = "原始考勤记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttendInitial attendInitial)
    {
        return toAjax(attendInitialService.updateAttendInitial(attendInitial));
    }

    /**
     * 删除原始考勤记录
     */
    @PreAuthorize("@ss.hasPermi('attendance:initial:remove')")
    @Log(title = "原始考勤记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{initialIds}")
    public AjaxResult remove(@PathVariable Long[] initialIds)
    {
        return toAjax(attendInitialService.deleteAttendInitialByInitialIds(initialIds));
    }
}
