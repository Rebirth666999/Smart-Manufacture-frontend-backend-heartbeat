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
import com.ruoyi.system.domain.vo.IcesProductInspectionVo;
import com.ruoyi.system.domain.bo.IcesProductInspectionBo;
import com.ruoyi.system.service.IIcesProductInspectionService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品质检单
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/productInspection")
public class IcesProductInspectionController extends BaseController {

    private final IIcesProductInspectionService iIcesProductInspectionService;

    /**
     * 查询产品质检单列表
     */
    @SaCheckPermission("system:productInspection:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProductInspectionVo> list(IcesProductInspectionBo bo, PageQuery pageQuery) {
        return iIcesProductInspectionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出产品质检单列表
     */
    @SaCheckPermission("system:productInspection:export")
    @Log(title = "产品质检单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProductInspectionBo bo, HttpServletResponse response) {
        List<IcesProductInspectionVo> list = iIcesProductInspectionService.queryList(bo);
        ExcelUtil.exportExcel(list, "产品质检单", IcesProductInspectionVo.class, response);
    }

    /**
     * 获取产品质检单详细信息
     *
     * @param piId 主键
     */
    @SaCheckPermission("system:productInspection:query")
    @GetMapping("/{piId}")
    public R<IcesProductInspectionVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long piId) {
        return R.ok(iIcesProductInspectionService.queryById(piId));
    }

    /**
     * 新增产品质检单
     */
    @SaCheckPermission("system:productInspection:add")
    @Log(title = "产品质检单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProductInspectionBo bo) {
        return toAjax(iIcesProductInspectionService.insertByBo(bo));
    }

    /**
     * 修改产品质检单
     */
    @SaCheckPermission("system:productInspection:edit")
    @Log(title = "产品质检单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProductInspectionBo bo) {
        return toAjax(iIcesProductInspectionService.updateByBo(bo));
    }

    /**
     * 删除产品质检单
     *
     * @param piIds 主键串
     */
    @SaCheckPermission("system:productInspection:remove")
    @Log(title = "产品质检单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{piIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] piIds) {
        return toAjax(iIcesProductInspectionService.deleteWithValidByIds(Arrays.asList(piIds), true));
    }
}
