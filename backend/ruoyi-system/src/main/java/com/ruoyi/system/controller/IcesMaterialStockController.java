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
import com.ruoyi.system.domain.vo.IcesMaterialStockVo;
import com.ruoyi.system.domain.bo.IcesMaterialStockBo;
import com.ruoyi.system.service.IIcesMaterialStockService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车间原料库存
 *
 * @author ruoyi
 * @date 2025-02-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/materialStock")
public class IcesMaterialStockController extends BaseController {

    private final IIcesMaterialStockService iIcesMaterialStockService;

    /**
     * 查询车间原料库存列表
     */
    @SaCheckPermission("system:materialStock:list")
    @GetMapping("/list")
    public TableDataInfo<IcesMaterialStockVo> list(IcesMaterialStockBo bo, PageQuery pageQuery) {
        return iIcesMaterialStockService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出车间原料库存列表
     */
    @SaCheckPermission("system:materialStock:export")
    @Log(title = "车间原料库存", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesMaterialStockBo bo, HttpServletResponse response) {
        List<IcesMaterialStockVo> list = iIcesMaterialStockService.queryList(bo);
        ExcelUtil.exportExcel(list, "车间原料库存", IcesMaterialStockVo.class, response);
    }

    /**
     * 获取车间原料库存详细信息
     *
     * @param msId 主键
     */
    @SaCheckPermission("system:materialStock:query")
    @GetMapping("/{msId}")
    public R<IcesMaterialStockVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long msId) {
        return R.ok(iIcesMaterialStockService.queryById(msId));
    }

    /**
     * 新增车间原料库存
     */
    @SaCheckPermission("system:materialStock:add")
    @Log(title = "车间原料库存", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesMaterialStockBo bo) {
        return toAjax(iIcesMaterialStockService.insertByBo(bo));
    }

    /**
     * 修改车间原料库存
     */
    @SaCheckPermission("system:materialStock:edit")
    @Log(title = "车间原料库存", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesMaterialStockBo bo) {
        return toAjax(iIcesMaterialStockService.updateByBo(bo));
    }

    /**
     * 删除车间原料库存
     *
     * @param msIds 主键串
     */
    @SaCheckPermission("system:materialStock:remove")
    @Log(title = "车间原料库存", businessType = BusinessType.DELETE)
    @DeleteMapping("/{msIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] msIds) {
        return toAjax(iIcesMaterialStockService.deleteWithValidByIds(Arrays.asList(msIds), true));
    }
}
