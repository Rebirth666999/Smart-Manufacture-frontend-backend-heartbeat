package com.ruoyi.system.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.system.domain.bo.IcesEquipmentModelBo;
import com.ruoyi.system.domain.vo.IcesEquipmentModelVo;
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
import com.ruoyi.system.domain.vo.IcesOrderVo;
import com.ruoyi.system.domain.bo.IcesOrderBo;
import com.ruoyi.system.service.IIcesOrderService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/order")
public class IcesOrderController extends BaseController {

    private final IIcesOrderService iIcesOrderService;

    /**
     * 查询订单列表
     */
    @SaCheckPermission("system:order:list")
    @GetMapping("/list")
    public TableDataInfo<IcesOrderVo> list(IcesOrderBo bo, PageQuery pageQuery) {
        return iIcesOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 审核端查询订单列表
     */
    @GetMapping("/reviewList")
    public TableDataInfo<IcesOrderVo> reviewList(IcesOrderBo bo, PageQuery pageQuery) {
        return iIcesOrderService.queryReviewList(bo, pageQuery);
    }
    /**
     * 导出订单列表
     */
    @SaCheckPermission("system:order:export")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesOrderBo bo, HttpServletResponse response) {
        List<IcesOrderVo> list = iIcesOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单", IcesOrderVo.class, response);
    }

    /**
     * 获取订单详细信息
     *
     * @param orId 主键
     */
    @SaCheckPermission("system:order:query")
    @GetMapping("/{orId}")
    public R<IcesOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long orId) {
        return R.ok(iIcesOrderService.queryById(orId));
    }

    /**
     * 新增订单
     */
    @SaCheckPermission("system:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<IcesOrderVo> add(@Validated(AddGroup.class) @RequestBody IcesOrderBo bo) {
        return R.ok(iIcesOrderService.insertByBo(bo));
    }

    /**
     * 修改订单
     */
    @SaCheckPermission("system:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<IcesOrderVo> edit(@Validated(EditGroup.class) @RequestBody IcesOrderBo bo) {
        return R.ok(iIcesOrderService.updateByBo(bo));
    }

    /**
     * 删除订单
     *
     * @param orIds 主键串
     */
    @SaCheckPermission("system:order:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] orIds) {
        return toAjax(iIcesOrderService.deleteWithValidByIds(Arrays.asList(orIds), true));
    }
}
