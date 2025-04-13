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
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVersionVo;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleVersionBo;
import com.ruoyi.system.service.IIcesExceptionLifecycleVersionService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常生命周期版本
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionLifecycleVersion")
public class IcesExceptionLifecycleVersionController extends BaseController {

    private final IIcesExceptionLifecycleVersionService iIcesExceptionLifecycleVersionService;

    /**
     * 查询异常生命周期版本列表
     */
    @SaCheckPermission("system:exceptionLifecycleVersion:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionLifecycleVersionVo> list(IcesExceptionLifecycleVersionBo bo, PageQuery pageQuery) {
        return iIcesExceptionLifecycleVersionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常生命周期版本列表
     */
    @SaCheckPermission("system:exceptionLifecycleVersion:export")
    @Log(title = "异常生命周期版本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionLifecycleVersionBo bo, HttpServletResponse response) {
        List<IcesExceptionLifecycleVersionVo> list = iIcesExceptionLifecycleVersionService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常生命周期版本", IcesExceptionLifecycleVersionVo.class, response);
    }

    /**
     * 获取异常生命周期版本详细信息
     *
     * @param exlvId 主键
     */
    @SaCheckPermission("system:exceptionLifecycleVersion:query")
    @GetMapping("/{exlvId}")
    public R<IcesExceptionLifecycleVersionVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exlvId) {
        return R.ok(iIcesExceptionLifecycleVersionService.queryById(exlvId));
    }

    /**
     * 新增异常生命周期版本
     */
    @SaCheckPermission("system:exceptionLifecycleVersion:add")
    @Log(title = "异常生命周期版本", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionLifecycleVersionBo bo) {
        return toAjax(iIcesExceptionLifecycleVersionService.insertByBo(bo));
    }

    /**
     * 修改异常生命周期版本
     */
    @SaCheckPermission("system:exceptionLifecycleVersion:edit")
    @Log(title = "异常生命周期版本", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionLifecycleVersionBo bo) {
        return toAjax(iIcesExceptionLifecycleVersionService.updateByBo(bo));
    }

    /**
     * 删除异常生命周期版本
     *
     * @param exlvIds 主键串
     */
    @SaCheckPermission("system:exceptionLifecycleVersion:remove")
    @Log(title = "异常生命周期版本", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exlvIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exlvIds) {
        return toAjax(iIcesExceptionLifecycleVersionService.deleteWithValidByIds(Arrays.asList(exlvIds), true));
    }
}
