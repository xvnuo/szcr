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
import com.ruoyi.attendance.domain.AttendSpecial;
import com.ruoyi.attendance.service.IAttendSpecialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常考勤Controller
 * 
 * @author xvnuo
 * @date 2022-01-23
 */
@RestController
@RequestMapping("/attendance/special")
public class AttendSpecialController extends BaseController
{
    @Autowired
    private IAttendSpecialService attendSpecialService;

    /**
     * 查询异常考勤列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:special:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttendSpecial attendSpecial)
    {
        startPage();
        List<AttendSpecial> list = attendSpecialService.selectAttendSpecialList(attendSpecial);
        return getDataTable(list);
    }

    /**
     * 导出异常考勤列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:special:export')")
    @Log(title = "异常考勤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttendSpecial attendSpecial)
    {
        List<AttendSpecial> list = attendSpecialService.selectAttendSpecialList(attendSpecial);
        ExcelUtil<AttendSpecial> util = new ExcelUtil<AttendSpecial>(AttendSpecial.class);
        util.exportExcel(response, list, "异常考勤数据");
    }

    /**
     * 获取异常考勤详细信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:special:query')")
    @GetMapping(value = "/{specialId}")
    public AjaxResult getInfo(@PathVariable("specialId") Long specialId)
    {
        return AjaxResult.success(attendSpecialService.selectAttendSpecialBySpecialId(specialId));
    }

    /**
     * 新增异常考勤
     */
    @PreAuthorize("@ss.hasPermi('attendance:special:add')")
    @Log(title = "异常考勤", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttendSpecial attendSpecial)
    {
        return toAjax(attendSpecialService.insertAttendSpecial(attendSpecial));
    }

    /**
     * 修改异常考勤
     */
    @PreAuthorize("@ss.hasPermi('attendance:special:edit')")
    @Log(title = "异常考勤", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttendSpecial attendSpecial)
    {
        return toAjax(attendSpecialService.updateAttendSpecial(attendSpecial));
    }

    /**
     * 删除异常考勤
     */
    @PreAuthorize("@ss.hasPermi('attendance:special:remove')")
    @Log(title = "异常考勤", businessType = BusinessType.DELETE)
	@DeleteMapping("/{specialIds}")
    public AjaxResult remove(@PathVariable Long[] specialIds)
    {
        return toAjax(attendSpecialService.deleteAttendSpecialBySpecialIds(specialIds));
    }
}
