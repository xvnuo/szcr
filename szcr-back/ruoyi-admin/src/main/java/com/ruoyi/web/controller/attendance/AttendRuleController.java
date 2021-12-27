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
import com.ruoyi.attendance.domain.AttendRule;
import com.ruoyi.attendance.service.IAttendRuleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考勤规则Controller
 *
 * @author xvnuo
 * @date 2021-12-19
 */
@RestController
@RequestMapping("/attendance/rule")
public class AttendRuleController extends BaseController
{
    @Autowired
    private IAttendRuleService attendRuleService;

    /**
     * 查询考勤规则列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttendRule attendRule)
    {
        startPage();
        List<AttendRule> list = attendRuleService.selectAttendRuleList(attendRule);
        return getDataTable(list);
    }

    /**
     * 导出考勤规则列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:export')")
    @Log(title = "考勤规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttendRule attendRule)
    {
        List<AttendRule> list = attendRuleService.selectAttendRuleList(attendRule);
        ExcelUtil<AttendRule> util = new ExcelUtil<AttendRule>(AttendRule.class);
        util.exportExcel(response, list, "考勤规则数据");
    }

    /**
     * 获取考勤规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:query')")
    @GetMapping(value = "/{ruleId}")
    public AjaxResult getInfo(@PathVariable("ruleId") Long ruleId)
    {
        return AjaxResult.success(attendRuleService.selectAttendRuleByRuleId(ruleId));
    }

    /**
     * 新增考勤规则
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:add')")
    @Log(title = "考勤规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttendRule attendRule)
    {
        return toAjax(attendRuleService.insertAttendRule(attendRule));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:edit')")
    @Log(title = "考勤规则", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody AttendRule rule)
    {
        return toAjax(attendRuleService.updateRuleStatus(rule));
    }

    /**
     * 修改考勤规则
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:edit')")
    @Log(title = "考勤规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttendRule attendRule)
    {
        return toAjax(attendRuleService.updateAttendRule(attendRule));
    }

    /**
     * 删除考勤规则
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:remove')")
    @Log(title = "考勤规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ruleIds}")
    public AjaxResult remove(@PathVariable Long[] ruleIds)
    {
        return toAjax(attendRuleService.deleteAttendRuleByRuleIds(ruleIds));
    }
}
