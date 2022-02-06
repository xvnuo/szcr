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
import com.ruoyi.attendance.domain.AttendStatistics;
import com.ruoyi.attendance.service.IAttendStatisticsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 月度考勤统计Controller
 *
 * @author xvnuo
 * @date 2022-01-23
 */
@RestController
@RequestMapping("/attendance/statistics")
public class AttendStatisticsController extends BaseController
{
    @Autowired
    private IAttendStatisticsService attendStatisticsService;

    /**
     * 查询月度考勤统计列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:statistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttendStatistics attendStatistics)
    {
        startPage();
        List<AttendStatistics> list = attendStatisticsService.selectAttendStatisticsList(attendStatistics);
        return getDataTable(list);
    }

    /**
     * 导出月度考勤统计列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:statistics:export')")
    @Log(title = "月度考勤统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttendStatistics attendStatistics)
    {
        List<AttendStatistics> list = attendStatisticsService.selectAttendStatisticsList(attendStatistics);
        ExcelUtil<AttendStatistics> util = new ExcelUtil<AttendStatistics>(AttendStatistics.class);
        util.exportExcel(response, list, "月度考勤统计数据");
    }

    /**
     * 获取月度考勤统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:statistics:query')")
    @GetMapping(value = "/{statisticsId}")
    public AjaxResult getInfo(@PathVariable("statisticsId") Long statisticsId)
    {
        return AjaxResult.success(attendStatisticsService.selectAttendStatisticsByStatisticsId(statisticsId));
    }

    /**
     * 新增月度考勤统计
     */
    @PreAuthorize("@ss.hasPermi('attendance:statistics:add')")
    @Log(title = "月度考勤统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttendStatistics attendStatistics)
    {
        return toAjax(attendStatisticsService.insertAttendStatistics(attendStatistics));
    }

    /**
     * 修改月度考勤统计
     */
    @PreAuthorize("@ss.hasPermi('attendance:statistics:edit')")
    @Log(title = "月度考勤统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttendStatistics attendStatistics)
    {
        return toAjax(attendStatisticsService.updateAttendStatistics(attendStatistics));
    }

    /**
     * 删除月度考勤统计
     */
    @PreAuthorize("@ss.hasPermi('attendance:statistics:remove')")
    @Log(title = "月度考勤统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{statisticsIds}")
    public AjaxResult remove(@PathVariable Long[] statisticsIds)
    {
        return toAjax(attendStatisticsService.deleteAttendStatisticsByStatisticsIds(statisticsIds));
    }
}
