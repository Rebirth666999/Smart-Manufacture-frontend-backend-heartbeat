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
import com.ruoyi.system.domain.vo.IcesProductStockVo;
import com.ruoyi.system.domain.bo.IcesProductStockBo;
import com.ruoyi.system.service.IIcesProductStockService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库产品库存
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/productStock")
public class IcesProductStockController extends BaseController {

    private final IIcesProductStockService iIcesProductStockService;

    /**
     * 查询仓库产品库存列表
     */
    @SaCheckPermission("system:productStock:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProductStockVo> list(IcesProductStockBo bo, PageQuery pageQuery) {
        return iIcesProductStockService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出仓库产品库存列表
     */
    @SaCheckPermission("system:productStock:export")
    @Log(title = "仓库产品库存", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProductStockBo bo, HttpServletResponse response) {
        List<IcesProductStockVo> list = iIcesProductStockService.queryList(bo);
        ExcelUtil.exportExcel(list, "仓库产品库存", IcesProductStockVo.class, response);
    }

    /**
     * 获取仓库产品库存详细信息
     *
     * @param pssId 主键
     */
    @SaCheckPermission("system:productStock:query")
    @GetMapping("/{pssId}")
    public R<IcesProductStockVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long pssId) {
        return R.ok(iIcesProductStockService.queryById(pssId));
    }

    /**
     * 新增仓库产品库存
     */
    @SaCheckPermission("system:productStock:add")
    @Log(title = "仓库产品库存", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProductStockBo bo) {
        return toAjax(iIcesProductStockService.insertByBo(bo));
    }

    /**
     * 修改仓库产品库存
     */
    @SaCheckPermission("system:productStock:edit")
    @Log(title = "仓库产品库存", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProductStockBo bo) {
        return toAjax(iIcesProductStockService.updateByBo(bo));
    }

    /**
     * 删除仓库产品库存
     *
     * @param pssIds 主键串
     */
    @SaCheckPermission("system:productStock:remove")
    @Log(title = "仓库产品库存", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pssIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] pssIds) {
        return toAjax(iIcesProductStockService.deleteWithValidByIds(Arrays.asList(pssIds), true));
    }
}
