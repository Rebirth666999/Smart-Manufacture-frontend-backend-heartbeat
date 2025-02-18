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
import com.ruoyi.system.domain.vo.IcesEquipmentModelTypeVo;
import com.ruoyi.system.domain.bo.IcesEquipmentModelTypeBo;
import com.ruoyi.system.service.IIcesEquipmentModelTypeService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备模型类型
 *
 * @author ruoyi
 * @date 2025-02-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/equipmentModelType")
public class IcesEquipmentModelTypeController extends BaseController {

    private final IIcesEquipmentModelTypeService iIcesEquipmentModelTypeService;

    /**
     * 查询设备模型类型列表
     */
    @SaCheckPermission("system:equipmentModelType:list")
    @GetMapping("/list")
    public TableDataInfo<IcesEquipmentModelTypeVo> list(IcesEquipmentModelTypeBo bo, PageQuery pageQuery) {
        return iIcesEquipmentModelTypeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备模型类型列表
     */
    @SaCheckPermission("system:equipmentModelType:export")
    @Log(title = "设备模型类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesEquipmentModelTypeBo bo, HttpServletResponse response) {
        List<IcesEquipmentModelTypeVo> list = iIcesEquipmentModelTypeService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备模型类型", IcesEquipmentModelTypeVo.class, response);
    }

    /**
     * 获取设备模型类型详细信息
     *
     * @param emtId 主键
     */
    @SaCheckPermission("system:equipmentModelType:query")
    @GetMapping("/{emtId}")
    public R<IcesEquipmentModelTypeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long emtId) {
        return R.ok(iIcesEquipmentModelTypeService.queryById(emtId));
    }

    /**
     * 新增设备模型类型
     */
    @SaCheckPermission("system:equipmentModelType:add")
    @Log(title = "设备模型类型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesEquipmentModelTypeBo bo) {
        return toAjax(iIcesEquipmentModelTypeService.insertByBo(bo));
    }

    /**
     * 修改设备模型类型
     */
    @SaCheckPermission("system:equipmentModelType:edit")
    @Log(title = "设备模型类型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesEquipmentModelTypeBo bo) {
        return toAjax(iIcesEquipmentModelTypeService.updateByBo(bo));
    }

    /**
     * 删除设备模型类型
     *
     * @param emtIds 主键串
     */
    @SaCheckPermission("system:equipmentModelType:remove")
    @Log(title = "设备模型类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{emtIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] emtIds) {
        return toAjax(iIcesEquipmentModelTypeService.deleteWithValidByIds(Arrays.asList(emtIds), true));
    }
}
