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
import com.ruoyi.system.domain.vo.IcesMaterialVo;
import com.ruoyi.system.domain.bo.IcesMaterialBo;
import com.ruoyi.system.service.IIcesMaterialService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 原料
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/material")
public class IcesMaterialController extends BaseController {

    private final IIcesMaterialService iIcesMaterialService;

    /**
     * 查询原料列表
     */
    @SaCheckPermission("system:material:list")
    @GetMapping("/list")
    public TableDataInfo<IcesMaterialVo> list(IcesMaterialBo bo, PageQuery pageQuery) {
        return iIcesMaterialService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出原料列表
     */
    @SaCheckPermission("system:material:export")
    @Log(title = "原料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesMaterialBo bo, HttpServletResponse response) {
        List<IcesMaterialVo> list = iIcesMaterialService.queryList(bo);
        ExcelUtil.exportExcel(list, "原料", IcesMaterialVo.class, response);
    }

    /**
     * 获取原料详细信息
     *
     * @param maId 主键
     */
    @SaCheckPermission("system:material:query")
    @GetMapping("/{maId}")
    public R<IcesMaterialVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long maId) {
        return R.ok(iIcesMaterialService.queryById(maId));
    }

    /**
     * 新增原料
     */
    @SaCheckPermission("system:material:add")
    @Log(title = "原料", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesMaterialBo bo) {
        return toAjax(iIcesMaterialService.insertByBo(bo));
    }

    /**
     * 修改原料
     */
    @SaCheckPermission("system:material:edit")
    @Log(title = "原料", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesMaterialBo bo) {
        return toAjax(iIcesMaterialService.updateByBo(bo));
    }

    /**
     * 删除原料
     *
     * @param maIds 主键串
     */
    @SaCheckPermission("system:material:remove")
    @Log(title = "原料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{maIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] maIds) {
        return toAjax(iIcesMaterialService.deleteWithValidByIds(Arrays.asList(maIds), true));
    }
}
