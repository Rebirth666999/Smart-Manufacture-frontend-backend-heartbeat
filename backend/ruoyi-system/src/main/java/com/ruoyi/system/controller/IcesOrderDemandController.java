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
import com.ruoyi.system.domain.vo.IcesOrderDemandVo;
import com.ruoyi.system.domain.bo.IcesOrderDemandBo;
import com.ruoyi.system.service.IIcesOrderDemandService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单所需产品关联
 *
 * @author ruoyi
 * @date 2025-04-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/orderDemand")
public class IcesOrderDemandController extends BaseController {

    private final IIcesOrderDemandService iIcesOrderDemandService;

    /**
     * 查询订单所需产品关联列表
     */
    @SaCheckPermission("system:orderDemand:list")
    @GetMapping("/list")
    public TableDataInfo<IcesOrderDemandVo> list(IcesOrderDemandBo bo, PageQuery pageQuery) {
        return iIcesOrderDemandService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单所需产品关联列表
     */
    @SaCheckPermission("system:orderDemand:export")
    @Log(title = "订单所需产品关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesOrderDemandBo bo, HttpServletResponse response) {
        List<IcesOrderDemandVo> list = iIcesOrderDemandService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单所需产品关联", IcesOrderDemandVo.class, response);
    }

    /**
     * 获取订单所需产品关联详细信息
     *
     * @param odId 主键
     */
    @SaCheckPermission("system:orderDemand:query")
    @GetMapping("/{odId}")
    public R<IcesOrderDemandVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long odId) {
        return R.ok(iIcesOrderDemandService.queryById(odId));
    }

    /**
     * 新增订单所需产品关联
     */
    @SaCheckPermission("system:orderDemand:add")
    @Log(title = "订单所需产品关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesOrderDemandBo bo) {
        return toAjax(iIcesOrderDemandService.insertByBo(bo));
    }

    /**
     * 修改订单所需产品关联
     */
    @SaCheckPermission("system:orderDemand:edit")
    @Log(title = "订单所需产品关联", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesOrderDemandBo bo) {
        return toAjax(iIcesOrderDemandService.updateByBo(bo));
    }

    /**
     * 删除订单所需产品关联
     *
     * @param odIds 主键串
     */
    @SaCheckPermission("system:orderDemand:remove")
    @Log(title = "订单所需产品关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{odIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] odIds) {
        return toAjax(iIcesOrderDemandService.deleteWithValidByIds(Arrays.asList(odIds), true));
    }
}
