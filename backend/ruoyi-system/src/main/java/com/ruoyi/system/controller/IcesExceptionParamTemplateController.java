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
import com.ruoyi.system.domain.vo.IcesExceptionParamTemplateVo;
import com.ruoyi.system.domain.bo.IcesExceptionParamTemplateBo;
import com.ruoyi.system.service.IIcesExceptionParamTemplateService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常参数模板
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionParamTemplate")
public class IcesExceptionParamTemplateController extends BaseController {

    private final IIcesExceptionParamTemplateService iIcesExceptionParamTemplateService;

    /**
     * 查询异常参数模板列表
     */
    @SaCheckPermission("system:exceptionParamTemplate:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionParamTemplateVo> list(IcesExceptionParamTemplateBo bo, PageQuery pageQuery) {
        return iIcesExceptionParamTemplateService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常参数模板列表
     */
    @SaCheckPermission("system:exceptionParamTemplate:export")
    @Log(title = "异常参数模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionParamTemplateBo bo, HttpServletResponse response) {
        List<IcesExceptionParamTemplateVo> list = iIcesExceptionParamTemplateService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常参数模板", IcesExceptionParamTemplateVo.class, response);
    }

    /**
     * 获取异常参数模板详细信息
     *
     * @param exptId 主键
     */
    @SaCheckPermission("system:exceptionParamTemplate:query")
    @GetMapping("/{exptId}")
    public R<IcesExceptionParamTemplateVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exptId) {
        return R.ok(iIcesExceptionParamTemplateService.queryById(exptId));
    }

    /**
     * 新增异常参数模板
     */
    @SaCheckPermission("system:exceptionParamTemplate:add")
    @Log(title = "异常参数模板", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionParamTemplateBo bo) {
        return toAjax(iIcesExceptionParamTemplateService.insertByBo(bo));
    }

    /**
     * 修改异常参数模板
     */
    @SaCheckPermission("system:exceptionParamTemplate:edit")
    @Log(title = "异常参数模板", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionParamTemplateBo bo) {
        return toAjax(iIcesExceptionParamTemplateService.updateByBo(bo));
    }

    /**
     * 删除异常参数模板
     *
     * @param exptIds 主键串
     */
    @SaCheckPermission("system:exceptionParamTemplate:remove")
    @Log(title = "异常参数模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exptIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exptIds) {
        return toAjax(iIcesExceptionParamTemplateService.deleteWithValidByIds(Arrays.asList(exptIds), true));
    }
}
