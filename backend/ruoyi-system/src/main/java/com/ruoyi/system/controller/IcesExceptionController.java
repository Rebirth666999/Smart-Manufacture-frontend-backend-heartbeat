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
import com.ruoyi.system.domain.vo.IcesExceptionVo;
import com.ruoyi.system.domain.bo.IcesExceptionBo;
import com.ruoyi.system.service.IIcesExceptionService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exception")
public class IcesExceptionController extends BaseController {

    private final IIcesExceptionService iIcesExceptionService;

    /**
     * 查询异常列表
     */
    @SaCheckPermission("system:exception:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionVo> list(IcesExceptionBo bo, PageQuery pageQuery) {
        return iIcesExceptionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常列表
     */
    @SaCheckPermission("system:exception:export")
    @Log(title = "异常", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionBo bo, HttpServletResponse response) {
        List<IcesExceptionVo> list = iIcesExceptionService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常", IcesExceptionVo.class, response);
    }

    /**
     * 获取异常详细信息
     *
     * @param exId 主键
     */
    @SaCheckPermission("system:exception:query")
    @GetMapping("/{exId}")
    public R<IcesExceptionVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exId) {
        return R.ok(iIcesExceptionService.queryById(exId));
    }

    /**
     * 新增异常
     */
    @SaCheckPermission("system:exception:add")
    @Log(title = "异常", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionBo bo) {
        return toAjax(iIcesExceptionService.insertByBo(bo));
    }

    /**
     * 修改异常
     */
    @SaCheckPermission("system:exception:edit")
    @Log(title = "异常", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionBo bo) {
        return toAjax(iIcesExceptionService.updateByBo(bo));
    }

    /**
     * 删除异常
     *
     * @param exIds 主键串
     */
    @SaCheckPermission("system:exception:remove")
    @Log(title = "异常", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exIds) {
        return toAjax(iIcesExceptionService.deleteWithValidByIds(Arrays.asList(exIds), true));
    }
}
