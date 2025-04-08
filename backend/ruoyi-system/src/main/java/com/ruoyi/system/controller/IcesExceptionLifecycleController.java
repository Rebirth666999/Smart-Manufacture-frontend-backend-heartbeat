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
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVo;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleBo;
import com.ruoyi.system.service.IIcesExceptionLifecycleService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常生命周期
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionLifecycle")
public class IcesExceptionLifecycleController extends BaseController {

    private final IIcesExceptionLifecycleService iIcesExceptionLifecycleService;

    /**
     * 查询异常生命周期列表
     */
    @SaCheckPermission("system:exceptionLifecycle:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionLifecycleVo> list(IcesExceptionLifecycleBo bo, PageQuery pageQuery) {
        return iIcesExceptionLifecycleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常生命周期列表
     */
    @SaCheckPermission("system:exceptionLifecycle:export")
    @Log(title = "异常生命周期", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionLifecycleBo bo, HttpServletResponse response) {
        List<IcesExceptionLifecycleVo> list = iIcesExceptionLifecycleService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常生命周期", IcesExceptionLifecycleVo.class, response);
    }

    /**
     * 获取异常生命周期详细信息
     *
     * @param exlId 主键
     */
    @SaCheckPermission("system:exceptionLifecycle:query")
    @GetMapping("/{exlId}")
    public R<IcesExceptionLifecycleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exlId) {
        return R.ok(iIcesExceptionLifecycleService.queryById(exlId));
    }

    /**
     * 新增异常生命周期
     */
    @SaCheckPermission("system:exceptionLifecycle:add")
    @Log(title = "异常生命周期", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionLifecycleBo bo) {
        return toAjax(iIcesExceptionLifecycleService.insertByBo(bo));
    }

    /**
     * 修改异常生命周期
     */
    @SaCheckPermission("system:exceptionLifecycle:edit")
    @Log(title = "异常生命周期", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionLifecycleBo bo) {
        return toAjax(iIcesExceptionLifecycleService.updateByBo(bo));
    }

    /**
     * 删除异常生命周期
     *
     * @param exlIds 主键串
     */
    @SaCheckPermission("system:exceptionLifecycle:remove")
    @Log(title = "异常生命周期", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exlIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exlIds) {
        return toAjax(iIcesExceptionLifecycleService.deleteWithValidByIds(Arrays.asList(exlIds), true));
    }
}
