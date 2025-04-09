package com.ruoyi.system.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.IcesExceptionWarningRecordVo;
import com.ruoyi.system.domain.bo.IcesExceptionWarningRecordBo;
import com.ruoyi.system.service.IIcesExceptionWarningRecordService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常预警记录
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionWarningRecord")
public class IcesExceptionWarningRecordController extends BaseController {

    private final IIcesExceptionWarningRecordService iIcesExceptionWarningRecordService;

    /**
     * 查询异常预警记录列表
     */
    @SaCheckPermission("system:exceptionWarningRecord:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionWarningRecordVo> list(IcesExceptionWarningRecordBo bo, PageQuery pageQuery) {
        return iIcesExceptionWarningRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常预警记录列表
     */
    @SaCheckPermission("system:exceptionWarningRecord:export")
    @Log(title = "异常预警记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionWarningRecordBo bo, HttpServletResponse response) {
        List<IcesExceptionWarningRecordVo> list = iIcesExceptionWarningRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常预警记录", IcesExceptionWarningRecordVo.class, response);
    }

    /**
     * 获取异常预警记录详细信息
     *
     * @param exwrId 主键
     */
    @SaCheckPermission("system:exceptionWarningRecord:query")
    @GetMapping("/{exwrId}")
    public R<IcesExceptionWarningRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exwrId) {
        return R.ok(iIcesExceptionWarningRecordService.queryById(exwrId));
    }

    /**
     * 新增异常预警记录
     */
    @SaCheckPermission("system:exceptionWarningRecord:add")
    @Log(title = "异常预警记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionWarningRecordBo bo) {
        return toAjax(iIcesExceptionWarningRecordService.insertByBo(bo));
    }

    /**
     * 修改异常预警记录
     */
    @SaCheckPermission("system:exceptionWarningRecord:edit")
    @Log(title = "异常预警记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionWarningRecordBo bo) {
        return toAjax(iIcesExceptionWarningRecordService.updateByBo(bo));
    }

    /**
     * 删除异常预警记录
     *
     * @param exwrIds 主键串
     */
    @SaCheckPermission("system:exceptionWarningRecord:remove")
    @Log(title = "异常预警记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exwrIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exwrIds) {
        return toAjax(iIcesExceptionWarningRecordService.deleteWithValidByIds(Arrays.asList(exwrIds), true));
    }
}
