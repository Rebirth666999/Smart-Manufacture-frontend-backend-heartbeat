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
import com.ruoyi.system.domain.vo.IcesExceptionParamVo;
import com.ruoyi.system.domain.bo.IcesExceptionParamBo;
import com.ruoyi.system.service.IIcesExceptionParamService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常参数
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionParam")
public class IcesExceptionParamController extends BaseController {

    private final IIcesExceptionParamService iIcesExceptionParamService;

    /**
     * 查询异常参数列表
     */
    @SaCheckPermission("system:exceptionParam:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionParamVo> list(IcesExceptionParamBo bo, PageQuery pageQuery) {
        return iIcesExceptionParamService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常参数列表
     */
    @SaCheckPermission("system:exceptionParam:export")
    @Log(title = "异常参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionParamBo bo, HttpServletResponse response) {
        List<IcesExceptionParamVo> list = iIcesExceptionParamService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常参数", IcesExceptionParamVo.class, response);
    }

    /**
     * 获取异常参数详细信息
     *
     * @param expId 主键
     */
    @SaCheckPermission("system:exceptionParam:query")
    @GetMapping("/{expId}")
    public R<IcesExceptionParamVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long expId) {
        return R.ok(iIcesExceptionParamService.queryById(expId));
    }

    /**
     * 新增异常参数
     */
    @SaCheckPermission("system:exceptionParam:add")
    @Log(title = "异常参数", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionParamBo bo) {
        return toAjax(iIcesExceptionParamService.insertByBo(bo));
    }

    /**
     * 修改异常参数
     */
    @SaCheckPermission("system:exceptionParam:edit")
    @Log(title = "异常参数", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionParamBo bo) {
        return toAjax(iIcesExceptionParamService.updateByBo(bo));
    }

    /**
     * 删除异常参数
     *
     * @param expIds 主键串
     */
    @SaCheckPermission("system:exceptionParam:remove")
    @Log(title = "异常参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{expIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] expIds) {
        return toAjax(iIcesExceptionParamService.deleteWithValidByIds(Arrays.asList(expIds), true));
    }
}
