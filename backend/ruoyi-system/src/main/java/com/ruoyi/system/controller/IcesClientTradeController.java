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
import com.ruoyi.system.domain.vo.IcesClientTradeVo;
import com.ruoyi.system.domain.bo.IcesClientTradeBo;
import com.ruoyi.system.service.IIcesClientTradeService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户贸易信息
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/clientTrade")
public class IcesClientTradeController extends BaseController {

    private final IIcesClientTradeService iIcesClientTradeService;

    /**
     * 查询客户贸易信息列表
     */
    @SaCheckPermission("system:clientTrade:list")
    @GetMapping("/list")
    public TableDataInfo<IcesClientTradeVo> list(IcesClientTradeBo bo, PageQuery pageQuery) {
        return iIcesClientTradeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出客户贸易信息列表
     */
    @SaCheckPermission("system:clientTrade:export")
    @Log(title = "客户贸易信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesClientTradeBo bo, HttpServletResponse response) {
        List<IcesClientTradeVo> list = iIcesClientTradeService.queryList(bo);
        ExcelUtil.exportExcel(list, "客户贸易信息", IcesClientTradeVo.class, response);
    }

    /**
     * 获取客户贸易信息详细信息
     *
     * @param ctId 主键
     */
    @SaCheckPermission("system:clientTrade:query")
    @GetMapping("/{ctId}")
    public R<IcesClientTradeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long ctId) {
        return R.ok(iIcesClientTradeService.queryById(ctId));
    }

    /**
     * 新增客户贸易信息
     */
    @SaCheckPermission("system:clientTrade:add")
    @Log(title = "客户贸易信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesClientTradeBo bo) {
        return toAjax(iIcesClientTradeService.insertByBo(bo));
    }

    /**
     * 修改客户贸易信息
     */
    @SaCheckPermission("system:clientTrade:edit")
    @Log(title = "客户贸易信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesClientTradeBo bo) {
        return toAjax(iIcesClientTradeService.updateByBo(bo));
    }

    /**
     * 删除客户贸易信息
     *
     * @param ctIds 主键串
     */
    @SaCheckPermission("system:clientTrade:remove")
    @Log(title = "客户贸易信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ctIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ctIds) {
        return toAjax(iIcesClientTradeService.deleteWithValidByIds(Arrays.asList(ctIds), true));
    }
}
