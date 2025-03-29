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
import com.ruoyi.system.domain.vo.IcesClientVo;
import com.ruoyi.system.domain.bo.IcesClientBo;
import com.ruoyi.system.service.IIcesClientService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户基本信息
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/client")
public class IcesClientController extends BaseController {

    private final IIcesClientService iIcesClientService;

    /**
     * 查询客户基本信息列表
     */
    @SaCheckPermission("system:client:list")
    @GetMapping("/list")
    public TableDataInfo<IcesClientVo> list(IcesClientBo bo, PageQuery pageQuery) {
        return iIcesClientService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出客户基本信息列表
     */
    @SaCheckPermission("system:client:export")
    @Log(title = "客户基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesClientBo bo, HttpServletResponse response) {
        List<IcesClientVo> list = iIcesClientService.queryList(bo);
        ExcelUtil.exportExcel(list, "客户基本信息", IcesClientVo.class, response);
    }

    /**
     * 获取客户基本信息详细信息
     *
     * @param clId 主键
     */
    @SaCheckPermission("system:client:query")
    @GetMapping("/{clId}")
    public R<IcesClientVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long clId) {
        return R.ok(iIcesClientService.queryById(clId));
    }

    /**
     * 新增客户基本信息
     */
    @SaCheckPermission("system:client:add")
    @Log(title = "客户基本信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesClientBo bo) {
        return toAjax(iIcesClientService.insertByBo(bo));
    }

    /**
     * 修改客户基本信息
     */
    @SaCheckPermission("system:client:edit")
    @Log(title = "客户基本信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesClientBo bo) {
        return toAjax(iIcesClientService.updateByBo(bo));
    }

    /**
     * 删除客户基本信息
     *
     * @param clIds 主键串
     */
    @SaCheckPermission("system:client:remove")
    @Log(title = "客户基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] clIds) {
        return toAjax(iIcesClientService.deleteWithValidByIds(Arrays.asList(clIds), true));
    }
}
