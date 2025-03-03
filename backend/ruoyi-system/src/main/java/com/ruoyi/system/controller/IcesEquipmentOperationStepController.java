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
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepVo;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationStepBo;
import com.ruoyi.system.service.IIcesEquipmentOperationStepService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备操作步骤
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/equipmentOperationStep")
public class IcesEquipmentOperationStepController extends BaseController {

    private final IIcesEquipmentOperationStepService iIcesEquipmentOperationStepService;

    /**
     * 查询设备操作步骤列表
     */
    @SaCheckPermission("system:equipmentOperationStep:list")
    @GetMapping("/list")
    public TableDataInfo<IcesEquipmentOperationStepVo> list(IcesEquipmentOperationStepBo bo, PageQuery pageQuery) {
        return iIcesEquipmentOperationStepService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备操作步骤列表
     */
    @SaCheckPermission("system:equipmentOperationStep:export")
    @Log(title = "设备操作步骤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesEquipmentOperationStepBo bo, HttpServletResponse response) {
        List<IcesEquipmentOperationStepVo> list = iIcesEquipmentOperationStepService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备操作步骤", IcesEquipmentOperationStepVo.class, response);
    }

    /**
     * 获取设备操作步骤详细信息
     *
     * @param eosId 主键
     */
    @SaCheckPermission("system:equipmentOperationStep:query")
    @GetMapping("/{eosId}")
    public R<IcesEquipmentOperationStepVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long eosId) {
        return R.ok(iIcesEquipmentOperationStepService.queryById(eosId));
    }

    /**
     * 新增设备操作步骤
     */
    @SaCheckPermission("system:equipmentOperationStep:add")
    @Log(title = "设备操作步骤", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesEquipmentOperationStepBo bo) {
        return toAjax(iIcesEquipmentOperationStepService.insertByBo(bo));
    }

    /**
     * 修改设备操作步骤
     */
    @SaCheckPermission("system:equipmentOperationStep:edit")
    @Log(title = "设备操作步骤", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesEquipmentOperationStepBo bo) {
        return toAjax(iIcesEquipmentOperationStepService.updateByBo(bo));
    }

    /**
     * 删除设备操作步骤
     *
     * @param eosIds 主键串
     */
    @SaCheckPermission("system:equipmentOperationStep:remove")
    @Log(title = "设备操作步骤", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eosIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] eosIds) {
        return toAjax(iIcesEquipmentOperationStepService.deleteWithValidByIds(Arrays.asList(eosIds), true));
    }
}
