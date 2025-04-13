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
import com.ruoyi.system.domain.vo.IcesExceptionWarningVo;
import com.ruoyi.system.domain.bo.IcesExceptionWarningBo;
import com.ruoyi.system.service.IIcesExceptionWarningService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常预警配置
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionWarning")
public class IcesExceptionWarningController extends BaseController {

    private final IIcesExceptionWarningService iIcesExceptionWarningService;

    /**
     * 查询异常预警配置列表
     */
    @SaCheckPermission("system:exceptionWarning:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionWarningVo> list(IcesExceptionWarningBo bo, PageQuery pageQuery) {
        return iIcesExceptionWarningService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常预警配置列表
     */
    @SaCheckPermission("system:exceptionWarning:export")
    @Log(title = "异常预警配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionWarningBo bo, HttpServletResponse response) {
        List<IcesExceptionWarningVo> list = iIcesExceptionWarningService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常预警配置", IcesExceptionWarningVo.class, response);
    }

    /**
     * 获取异常预警配置详细信息
     *
     * @param exwId 主键
     */
    @SaCheckPermission("system:exceptionWarning:query")
    @GetMapping("/{exwId}")
    public R<IcesExceptionWarningVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exwId) {
        return R.ok(iIcesExceptionWarningService.queryById(exwId));
    }

    /**
     * 新增异常预警配置
     */
    @SaCheckPermission("system:exceptionWarning:add")
    @Log(title = "异常预警配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionWarningBo bo) {
        return toAjax(iIcesExceptionWarningService.insertByBo(bo));
    }

    /**
     * 修改异常预警配置
     */
    @SaCheckPermission("system:exceptionWarning:edit")
    @Log(title = "异常预警配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionWarningBo bo) {
        return toAjax(iIcesExceptionWarningService.updateByBo(bo));
    }

    /**
     * 删除异常预警配置
     *
     * @param exwIds 主键串
     */
    @SaCheckPermission("system:exceptionWarning:remove")
    @Log(title = "异常预警配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exwIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exwIds) {
        return toAjax(iIcesExceptionWarningService.deleteWithValidByIds(Arrays.asList(exwIds), true));
    }
}
