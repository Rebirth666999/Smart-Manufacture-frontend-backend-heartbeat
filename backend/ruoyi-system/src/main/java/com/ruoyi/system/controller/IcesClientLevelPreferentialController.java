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
import com.ruoyi.system.domain.vo.IcesClientLevelPreferentialVo;
import com.ruoyi.system.domain.bo.IcesClientLevelPreferentialBo;
import com.ruoyi.system.service.IIcesClientLevelPreferentialService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 关联-客户等级对应的优惠策略
 *
 * @author ruoyi
 * @date 2025-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/clientLevelPreferential")
public class IcesClientLevelPreferentialController extends BaseController {

    private final IIcesClientLevelPreferentialService iIcesClientLevelPreferentialService;

    /**
     * 查询关联-客户等级对应的优惠策略列表
     */
    @SaCheckPermission("system:clientLevelPreferential:list")
    @GetMapping("/list")
    public TableDataInfo<IcesClientLevelPreferentialVo> list(IcesClientLevelPreferentialBo bo, PageQuery pageQuery) {
        return iIcesClientLevelPreferentialService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出关联-客户等级对应的优惠策略列表
     */
    @SaCheckPermission("system:clientLevelPreferential:export")
    @Log(title = "关联-客户等级对应的优惠策略", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesClientLevelPreferentialBo bo, HttpServletResponse response) {
        List<IcesClientLevelPreferentialVo> list = iIcesClientLevelPreferentialService.queryList(bo);
        ExcelUtil.exportExcel(list, "关联-客户等级对应的优惠策略", IcesClientLevelPreferentialVo.class, response);
    }

    /**
     * 获取关联-客户等级对应的优惠策略详细信息
     *
     * @param clpId 主键
     */
    @SaCheckPermission("system:clientLevelPreferential:query")
    @GetMapping("/{clpId}")
    public R<IcesClientLevelPreferentialVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long clpId) {
        return R.ok(iIcesClientLevelPreferentialService.queryById(clpId));
    }

    /**
     * 新增关联-客户等级对应的优惠策略
     */
    @SaCheckPermission("system:clientLevelPreferential:add")
    @Log(title = "关联-客户等级对应的优惠策略", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesClientLevelPreferentialBo bo) {
        return toAjax(iIcesClientLevelPreferentialService.insertByBo(bo));
    }

    /**
     * 修改关联-客户等级对应的优惠策略
     */
    @SaCheckPermission("system:clientLevelPreferential:edit")
    @Log(title = "关联-客户等级对应的优惠策略", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesClientLevelPreferentialBo bo) {
        return toAjax(iIcesClientLevelPreferentialService.updateByBo(bo));
    }

    /**
     * 删除关联-客户等级对应的优惠策略
     *
     * @param clpIds 主键串
     */
    @SaCheckPermission("system:clientLevelPreferential:remove")
    @Log(title = "关联-客户等级对应的优惠策略", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clpIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] clpIds) {
        return toAjax(iIcesClientLevelPreferentialService.deleteWithValidByIds(Arrays.asList(clpIds), true));
    }
}
