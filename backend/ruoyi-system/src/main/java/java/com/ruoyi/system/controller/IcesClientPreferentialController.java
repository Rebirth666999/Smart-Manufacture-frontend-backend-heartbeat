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
import com.ruoyi.system.domain.vo.IcesClientPreferentialVo;
import com.ruoyi.system.domain.bo.IcesClientPreferentialBo;
import com.ruoyi.system.service.IIcesClientPreferentialService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户优惠策略
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/clientPreferential")
public class IcesClientPreferentialController extends BaseController {

    private final IIcesClientPreferentialService iIcesClientPreferentialService;

    /**
     * 查询客户优惠策略列表
     */
    @SaCheckPermission("system:clientPreferential:list")
    @GetMapping("/list")
    public TableDataInfo<IcesClientPreferentialVo> list(IcesClientPreferentialBo bo, PageQuery pageQuery) {
        return iIcesClientPreferentialService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出客户优惠策略列表
     */
    @SaCheckPermission("system:clientPreferential:export")
    @Log(title = "客户优惠策略", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesClientPreferentialBo bo, HttpServletResponse response) {
        List<IcesClientPreferentialVo> list = iIcesClientPreferentialService.queryList(bo);
        ExcelUtil.exportExcel(list, "客户优惠策略", IcesClientPreferentialVo.class, response);
    }

    /**
     * 获取客户优惠策略详细信息
     *
     * @param cpId 主键
     */
    @SaCheckPermission("system:clientPreferential:query")
    @GetMapping("/{cpId}")
    public R<IcesClientPreferentialVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long cpId) {
        return R.ok(iIcesClientPreferentialService.queryById(cpId));
    }

    /**
     * 新增客户优惠策略
     */
    @SaCheckPermission("system:clientPreferential:add")
    @Log(title = "客户优惠策略", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesClientPreferentialBo bo) {
        return toAjax(iIcesClientPreferentialService.insertByBo(bo));
    }

    /**
     * 修改客户优惠策略
     */
    @SaCheckPermission("system:clientPreferential:edit")
    @Log(title = "客户优惠策略", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesClientPreferentialBo bo) {
        return toAjax(iIcesClientPreferentialService.updateByBo(bo));
    }

    /**
     * 删除客户优惠策略
     *
     * @param cpIds 主键串
     */
    @SaCheckPermission("system:clientPreferential:remove")
    @Log(title = "客户优惠策略", businessType = BusinessType.DELETE)
    @DeleteMapping("/{cpIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] cpIds) {
        return toAjax(iIcesClientPreferentialService.deleteWithValidByIds(Arrays.asList(cpIds), true));
    }
}
