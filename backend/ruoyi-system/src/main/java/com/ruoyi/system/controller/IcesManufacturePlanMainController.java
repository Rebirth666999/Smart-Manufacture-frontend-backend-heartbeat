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
import com.ruoyi.system.domain.vo.IcesManufacturePlanMainVo;
import com.ruoyi.system.domain.bo.IcesManufacturePlanMainBo;
import com.ruoyi.system.service.IIcesManufacturePlanMainService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生产计划(主)
 *
 * @author ruoyi
 * @date 2025-04-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/manufacturePlanMain")
public class IcesManufacturePlanMainController extends BaseController {

    private final IIcesManufacturePlanMainService iIcesManufacturePlanMainService;

    /**
     * 查询生产计划(主)列表
     */
    @SaCheckPermission("system:manufacturePlanMain:list")
    @GetMapping("/list")
    public TableDataInfo<IcesManufacturePlanMainVo> list(IcesManufacturePlanMainBo bo, PageQuery pageQuery) {
        return iIcesManufacturePlanMainService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出生产计划(主)列表
     */
    @SaCheckPermission("system:manufacturePlanMain:export")
    @Log(title = "生产计划(主)", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesManufacturePlanMainBo bo, HttpServletResponse response) {
        List<IcesManufacturePlanMainVo> list = iIcesManufacturePlanMainService.queryList(bo);
        ExcelUtil.exportExcel(list, "生产计划(主)", IcesManufacturePlanMainVo.class, response);
    }

    /**
     * 获取生产计划(主)详细信息
     *
     * @param mpmId 主键
     */
    @SaCheckPermission("system:manufacturePlanMain:query")
    @GetMapping("/{mpmId}")
    public R<IcesManufacturePlanMainVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long mpmId) {
        return R.ok(iIcesManufacturePlanMainService.queryById(mpmId));
    }

    /**
     * 新增生产计划(主)
     */
    @SaCheckPermission("system:manufacturePlanMain:add")
    @Log(title = "生产计划(主)", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesManufacturePlanMainBo bo) {
        return toAjax(iIcesManufacturePlanMainService.insertByBo(bo));
    }

    /**
     * 修改生产计划(主)
     */
    @SaCheckPermission("system:manufacturePlanMain:edit")
    @Log(title = "生产计划(主)", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesManufacturePlanMainBo bo) {
        return toAjax(iIcesManufacturePlanMainService.updateByBo(bo));
    }

    /**
     * 删除生产计划(主)
     *
     * @param mpmIds 主键串
     */
    @SaCheckPermission("system:manufacturePlanMain:remove")
    @Log(title = "生产计划(主)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{mpmIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] mpmIds) {
        return toAjax(iIcesManufacturePlanMainService.deleteWithValidByIds(Arrays.asList(mpmIds), true));
    }
}
