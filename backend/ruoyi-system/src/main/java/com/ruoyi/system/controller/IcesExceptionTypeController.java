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
import com.ruoyi.system.domain.vo.IcesExceptionTypeVo;
import com.ruoyi.system.domain.bo.IcesExceptionTypeBo;
import com.ruoyi.system.service.IIcesExceptionTypeService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常类型
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionType")
public class IcesExceptionTypeController extends BaseController {

    private final IIcesExceptionTypeService iIcesExceptionTypeService;

    /**
     * 查询异常类型列表
     */
    @SaCheckPermission("system:exceptionType:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionTypeVo> list(IcesExceptionTypeBo bo, PageQuery pageQuery) {
        return iIcesExceptionTypeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常类型列表
     */
    @SaCheckPermission("system:exceptionType:export")
    @Log(title = "异常类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionTypeBo bo, HttpServletResponse response) {
        List<IcesExceptionTypeVo> list = iIcesExceptionTypeService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常类型", IcesExceptionTypeVo.class, response);
    }

    /**
     * 获取异常类型详细信息
     *
     * @param extId 主键
     */
    @SaCheckPermission("system:exceptionType:query")
    @GetMapping("/{extId}")
    public R<IcesExceptionTypeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long extId) {
        return R.ok(iIcesExceptionTypeService.queryById(extId));
    }

    /**
     * 新增异常类型
     */
    @SaCheckPermission("system:exceptionType:add")
    @Log(title = "异常类型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionTypeBo bo) {
        return toAjax(iIcesExceptionTypeService.insertByBo(bo));
    }

    /**
     * 修改异常类型
     */
    @SaCheckPermission("system:exceptionType:edit")
    @Log(title = "异常类型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionTypeBo bo) {
        return toAjax(iIcesExceptionTypeService.updateByBo(bo));
    }

    /**
     * 删除异常类型
     *
     * @param extIds 主键串
     */
    @SaCheckPermission("system:exceptionType:remove")
    @Log(title = "异常类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{extIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] extIds) {
        return toAjax(iIcesExceptionTypeService.deleteWithValidByIds(Arrays.asList(extIds), true));
    }
}
