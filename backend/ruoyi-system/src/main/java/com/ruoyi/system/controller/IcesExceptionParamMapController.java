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
import com.ruoyi.system.domain.vo.IcesExceptionParamMapVo;
import com.ruoyi.system.domain.bo.IcesExceptionParamMapBo;
import com.ruoyi.system.service.IIcesExceptionParamMapService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常参数模板条目
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionParamMap")
public class IcesExceptionParamMapController extends BaseController {

    private final IIcesExceptionParamMapService iIcesExceptionParamMapService;

    /**
     * 查询异常参数模板条目列表
     */
    @SaCheckPermission("system:exceptionParamMap:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionParamMapVo> list(IcesExceptionParamMapBo bo, PageQuery pageQuery) {
        return iIcesExceptionParamMapService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常参数模板条目列表
     */
    @SaCheckPermission("system:exceptionParamMap:export")
    @Log(title = "异常参数模板条目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionParamMapBo bo, HttpServletResponse response) {
        List<IcesExceptionParamMapVo> list = iIcesExceptionParamMapService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常参数模板条目", IcesExceptionParamMapVo.class, response);
    }

    /**
     * 获取异常参数模板条目详细信息
     *
     * @param expmId 主键
     */
    @SaCheckPermission("system:exceptionParamMap:query")
    @GetMapping("/{expmId}")
    public R<IcesExceptionParamMapVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long expmId) {
        return R.ok(iIcesExceptionParamMapService.queryById(expmId));
    }

    /**
     * 新增异常参数模板条目
     */
    @SaCheckPermission("system:exceptionParamMap:add")
    @Log(title = "异常参数模板条目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionParamMapBo bo) {
        return toAjax(iIcesExceptionParamMapService.insertByBo(bo));
    }

    /**
     * 修改异常参数模板条目
     */
    @SaCheckPermission("system:exceptionParamMap:edit")
    @Log(title = "异常参数模板条目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionParamMapBo bo) {
        return toAjax(iIcesExceptionParamMapService.updateByBo(bo));
    }

    /**
     * 删除异常参数模板条目
     *
     * @param expmIds 主键串
     */
    @SaCheckPermission("system:exceptionParamMap:remove")
    @Log(title = "异常参数模板条目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{expmIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] expmIds) {
        return toAjax(iIcesExceptionParamMapService.deleteWithValidByIds(Arrays.asList(expmIds), true));
    }
}
