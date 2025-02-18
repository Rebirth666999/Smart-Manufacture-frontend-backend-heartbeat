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
import com.ruoyi.system.domain.vo.IcesEquipmentModelVo;
import com.ruoyi.system.domain.bo.IcesEquipmentModelBo;
import com.ruoyi.system.service.IIcesEquipmentModelService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备模型
 *
 * @author ruoyi
 * @date 2025-02-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/equipmentModel")
public class IcesEquipmentModelController extends BaseController {

    private final IIcesEquipmentModelService iIcesEquipmentModelService;

    /**
     * 查询设备模型列表
     */
    @SaCheckPermission("system:equipmentModel:list")
    @GetMapping("/list")
    public TableDataInfo<IcesEquipmentModelVo> list(IcesEquipmentModelBo bo, PageQuery pageQuery) {
        return iIcesEquipmentModelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备模型列表
     */
    @SaCheckPermission("system:equipmentModel:export")
    @Log(title = "设备模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesEquipmentModelBo bo, HttpServletResponse response) {
        List<IcesEquipmentModelVo> list = iIcesEquipmentModelService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备模型", IcesEquipmentModelVo.class, response);
    }

    /**
     * 获取设备模型详细信息
     *
     * @param emId 主键
     */
    @SaCheckPermission("system:equipmentModel:query")
    @GetMapping("/{emId}")
    public R<IcesEquipmentModelVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long emId) {
        return R.ok(iIcesEquipmentModelService.queryById(emId));
    }

    /**
     * 新增设备模型
     */
    @SaCheckPermission("system:equipmentModel:add")
    @Log(title = "设备模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesEquipmentModelBo bo) {
        return toAjax(iIcesEquipmentModelService.insertByBo(bo));
    }

    /**
     * 修改设备模型
     */
    @SaCheckPermission("system:equipmentModel:edit")
    @Log(title = "设备模型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesEquipmentModelBo bo) {
        return toAjax(iIcesEquipmentModelService.updateByBo(bo));
    }

    /**
     * 删除设备模型
     *
     * @param emIds 主键串
     */
    @SaCheckPermission("system:equipmentModel:remove")
    @Log(title = "设备模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{emIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] emIds) {
        return toAjax(iIcesEquipmentModelService.deleteWithValidByIds(Arrays.asList(emIds), true));
    }
}
