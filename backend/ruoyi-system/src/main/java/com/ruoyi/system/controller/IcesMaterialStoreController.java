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
import com.ruoyi.system.domain.vo.IcesMaterialStoreVo;
import com.ruoyi.system.domain.bo.IcesMaterialStoreBo;
import com.ruoyi.system.service.IIcesMaterialStoreService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 原料仓库
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/materialStore")
public class IcesMaterialStoreController extends BaseController {

    private final IIcesMaterialStoreService iIcesMaterialStoreService;

    /**
     * 查询原料仓库列表
     */
    @SaCheckPermission("system:materialStore:list")
    @GetMapping("/list")
    public TableDataInfo<IcesMaterialStoreVo> list(IcesMaterialStoreBo bo, PageQuery pageQuery) {
        return iIcesMaterialStoreService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出原料仓库列表
     */
    @SaCheckPermission("system:materialStore:export")
    @Log(title = "原料仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesMaterialStoreBo bo, HttpServletResponse response) {
        List<IcesMaterialStoreVo> list = iIcesMaterialStoreService.queryList(bo);
        ExcelUtil.exportExcel(list, "原料仓库", IcesMaterialStoreVo.class, response);
    }

    /**
     * 获取原料仓库详细信息
     *
     * @param msId 主键
     */
    @SaCheckPermission("system:materialStore:query")
    @GetMapping("/{msId}")
    public R<IcesMaterialStoreVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long msId) {
        return R.ok(iIcesMaterialStoreService.queryById(msId));
    }

    /**
     * 新增原料仓库
     */
    @SaCheckPermission("system:materialStore:add")
    @Log(title = "原料仓库", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesMaterialStoreBo bo) {
        return toAjax(iIcesMaterialStoreService.insertByBo(bo));
    }

    /**
     * 修改原料仓库
     */
    @SaCheckPermission("system:materialStore:edit")
    @Log(title = "原料仓库", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesMaterialStoreBo bo) {
        return toAjax(iIcesMaterialStoreService.updateByBo(bo));
    }

    /**
     * 删除原料仓库
     *
     * @param msIds 主键串
     */
    @SaCheckPermission("system:materialStore:remove")
    @Log(title = "原料仓库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{msIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] msIds) {
        return toAjax(iIcesMaterialStoreService.deleteWithValidByIds(Arrays.asList(msIds), true));
    }
}
