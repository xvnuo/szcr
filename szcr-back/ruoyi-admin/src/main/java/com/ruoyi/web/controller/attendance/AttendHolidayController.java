package com.ruoyi.web.controller.attendance;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
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
import com.ruoyi.attendance.domain.AttendHoliday;
import com.ruoyi.attendance.service.IAttendHolidayService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 节假日信息Controller
 *
 * @author xvnuo
 * @date 2021-12-18
 */
@RestController
@RequestMapping("/attendance/holiday")
public class AttendHolidayController extends BaseController
{
    @Autowired
    private IAttendHolidayService attendHolidayService;

    /**
     * 查询节假日信息列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:holiday:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttendHoliday attendHoliday)
    {
        startPage();
        List<AttendHoliday> list = attendHolidayService.selectAttendHolidayList(attendHoliday);
        return getDataTable(list);
    }

    /**
     * 导出节假日信息列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:holiday:export')")
    @Log(title = "节假日信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttendHoliday attendHoliday)
    {
        List<AttendHoliday> list = attendHolidayService.selectAttendHolidayList(attendHoliday);
        ExcelUtil<AttendHoliday> util = new ExcelUtil<AttendHoliday>(AttendHoliday.class);
        util.exportExcel(response, list, "节假日信息数据");
    }

    /**
     * 获取节假日信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:holiday:query')")
    @GetMapping(value = "/{holidayId}")
    public AjaxResult getInfo(@PathVariable("holidayId") Long holidayId)
    {
        return AjaxResult.success(attendHolidayService.selectAttendHolidayByHolidayId(holidayId));
    }

    /**
     * 新增节假日信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:holiday:add')")
    @Log(title = "节假日信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttendHoliday attendHoliday)
    {
        return toAjax(attendHolidayService.insertAttendHoliday(attendHoliday));
    }

    /**
     * 修改节假日信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:holiday:edit')")
    @Log(title = "节假日信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttendHoliday attendHoliday)
    {
        return toAjax(attendHolidayService.updateAttendHoliday(attendHoliday));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:holiday:edit')")
    @Log(title = "节假日管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody AttendHoliday holiday)
    {
        holiday.setUpdateBy(getUsername());
        return toAjax(attendHolidayService.updateHolidayStatus(holiday));
    }

    /**
     * 删除节假日信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:holiday:remove')")
    @Log(title = "节假日信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{holidayIds}")
    public AjaxResult remove(@PathVariable Long[] holidayIds)
    {
        return toAjax(attendHolidayService.deleteAttendHolidayByHolidayIds(holidayIds));
    }
}
