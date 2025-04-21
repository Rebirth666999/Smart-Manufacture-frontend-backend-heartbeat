package com.ruoyi.system.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.system.domain.bo.IcesProductInspectionBo;
import com.ruoyi.system.domain.vo.IcesProductVo;
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
import com.ruoyi.system.domain.vo.IcesProductInspectionDetailVo;
import com.ruoyi.system.domain.bo.IcesProductInspectionDetailBo;
import com.ruoyi.system.service.IIcesProductInspectionDetailService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品质检单明细
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/productInspectionDetail")
public class IcesProductInspectionDetailController extends BaseController {

    private final IIcesProductInspectionDetailService iIcesProductInspectionDetailService;

    /**
     * 查询产品质检单明细列表
     */
    @SaCheckPermission("system:productInspectionDetail:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProductInspectionDetailVo> list(IcesProductInspectionDetailBo bo, PageQuery pageQuery) {
        return iIcesProductInspectionDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 根据质检单获取产品
     */
    @PostMapping("/getProduct")
    public IcesProductVo list(IcesProductInspectionBo bo) {
        return iIcesProductInspectionDetailService.getProduct(bo);
    }

    /**
     * 导出产品质检单明细列表
     */
    @SaCheckPermission("system:productInspectionDetail:export")
    @Log(title = "产品质检单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProductInspectionDetailBo bo, HttpServletResponse response) {
        List<IcesProductInspectionDetailVo> list = iIcesProductInspectionDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "产品质检单明细", IcesProductInspectionDetailVo.class, response);
    }

    /**
     * 获取产品质检单明细详细信息
     *
     * @param pidId 主键
     */
    @SaCheckPermission("system:productInspectionDetail:query")
    @GetMapping("/{pidId}")
    public R<IcesProductInspectionDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long pidId) {
        return R.ok(iIcesProductInspectionDetailService.queryById(pidId));
    }

    /**
     * 新增产品质检单明细
     */
    @SaCheckPermission("system:productInspectionDetail:add")
    @Log(title = "产品质检单明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProductInspectionDetailBo bo) {
        return toAjax(iIcesProductInspectionDetailService.insertByBo(bo));
    }

    /**
     * 修改产品质检单明细
     */
    @SaCheckPermission("system:productInspectionDetail:edit")
    @Log(title = "产品质检单明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProductInspectionDetailBo bo) {
        return toAjax(iIcesProductInspectionDetailService.updateByBo(bo));
    }

    /**
     * 删除产品质检单明细
     *
     * @param pidIds 主键串
     */
    @SaCheckPermission("system:productInspectionDetail:remove")
    @Log(title = "产品质检单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pidIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] pidIds) {
        return toAjax(iIcesProductInspectionDetailService.deleteWithValidByIds(Arrays.asList(pidIds), true));
    }
}
