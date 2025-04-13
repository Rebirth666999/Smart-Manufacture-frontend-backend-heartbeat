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
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.service.IIcesManufacturePlanService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生产计划
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/manufacturePlan")
public class IcesManufacturePlanController extends BaseController {

    private final IIcesManufacturePlanService iIcesManufacturePlanService;

    /**
     * 查询生产计划列表
     */
    @SaCheckPermission("system:manufacturePlan:list")
    @GetMapping("/list")
    public TableDataInfo<IcesManufacturePlanVo> list(IcesManufacturePlanBo bo, PageQuery pageQuery) {
        return iIcesManufacturePlanService.queryPageList(bo, pageQuery);
    }

    /**
     * 审核端查询列表
     */
    @GetMapping("/reviewList")
    public TableDataInfo<IcesManufacturePlanVo> reviewList(IcesManufacturePlanBo bo, PageQuery pageQuery) {
        return iIcesManufacturePlanService.queryReviewList(bo, pageQuery);
    }

    /**
     * 导出生产计划列表
     */
    @SaCheckPermission("system:manufacturePlan:export")
    @Log(title = "生产计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesManufacturePlanBo bo, HttpServletResponse response) {
        List<IcesManufacturePlanVo> list = iIcesManufacturePlanService.queryList(bo);
        ExcelUtil.exportExcel(list, "生产计划", IcesManufacturePlanVo.class, response);
    }

    /**
     * 获取生产计划详细信息
     *
     * @param mpId 主键
     */
    @SaCheckPermission("system:manufacturePlan:query")
    @GetMapping("/{mpId}")
    public R<IcesManufacturePlanVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long mpId) {
        return R.ok(iIcesManufacturePlanService.queryById(mpId));
    }

    /**
     * 新增生产计划
     */
    @SaCheckPermission("system:manufacturePlan:add")
    @Log(title = "生产计划", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<IcesManufacturePlanVo> add(@Validated(AddGroup.class) @RequestBody IcesManufacturePlanBo bo) {
        return R.ok(iIcesManufacturePlanService.insertByBo(bo));
    }

    /**
     * 修改生产计划
     */
    @SaCheckPermission("system:manufacturePlan:edit")
    @Log(title = "生产计划", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesManufacturePlanBo bo) {
        return toAjax(iIcesManufacturePlanService.updateByBo(bo));
    }

    /**
     * 删除生产计划
     *
     * @param mpIds 主键串
     */
    @SaCheckPermission("system:manufacturePlan:remove")
    @Log(title = "生产计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{mpIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] mpIds) {
        return toAjax(iIcesManufacturePlanService.deleteWithValidByIds(Arrays.asList(mpIds), true));
    }
}
