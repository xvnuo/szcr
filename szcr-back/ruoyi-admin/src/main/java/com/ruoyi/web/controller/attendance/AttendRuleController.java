package com.ruoyi.web.controller.attendance;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.AttendRule;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
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
import com.ruoyi.attendance.service.IAttendRuleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ISysUserService userService;

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

    /**
     * 查询已分配该规则的用户列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:list')")
    @GetMapping("/authUser/assignedList")
    public TableDataInfo assignedList(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectAssignedList(user);
        return getDataTable(list);
    }

    /**
     * 查询未分配该规则的用户列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:list')")
    @GetMapping("/authUser/unassignedList")
    public TableDataInfo unassignedList(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectUnassignedList(user);
        return getDataTable(list);
    }

    /**
     * 批量取消分配用户考勤规则
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    public AjaxResult cancelAuthUserAll(Long ruleId, Long[] userIds)
    {
        return toAjax(attendRuleService.cancelUsersRule(userIds));
    }

    /**
     * 批量选择用户分配以考勤规则
     */
    @PreAuthorize("@ss.hasPermi('attendance:rule:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    public AjaxResult selectAuthUserAll(Long ruleId, Long[] userIds)
    {
        return toAjax(attendRuleService.insertUsersRule(ruleId, userIds));
    }

    /**
     * 通过Excel文件批量导入用户与考勤规则的关联关系
     * @param file 文件
     * @return 结果
     * @throws Exception 异常
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('attendance:rule:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, Long ruleId, Boolean isUpdateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = attendRuleService.importUser(userList, operName, ruleId, isUpdateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 提供用户导入Excel模板文件
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }
}
