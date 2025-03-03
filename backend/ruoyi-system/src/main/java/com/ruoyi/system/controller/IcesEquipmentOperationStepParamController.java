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
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepParamVo;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationStepParamBo;
import com.ruoyi.system.service.IIcesEquipmentOperationStepParamService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备操作步骤参数
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/equipmentOperationStepParam")
public class IcesEquipmentOperationStepParamController extends BaseController {

    private final IIcesEquipmentOperationStepParamService iIcesEquipmentOperationStepParamService;

    /**
     * 查询设备操作步骤参数列表
     */
    @SaCheckPermission("system:equipmentOperationStepParam:list")
    @GetMapping("/list")
    public TableDataInfo<IcesEquipmentOperationStepParamVo> list(IcesEquipmentOperationStepParamBo bo, PageQuery pageQuery) {
        return iIcesEquipmentOperationStepParamService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备操作步骤参数列表
     */
    @SaCheckPermission("system:equipmentOperationStepParam:export")
    @Log(title = "设备操作步骤参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesEquipmentOperationStepParamBo bo, HttpServletResponse response) {
        List<IcesEquipmentOperationStepParamVo> list = iIcesEquipmentOperationStepParamService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备操作步骤参数", IcesEquipmentOperationStepParamVo.class, response);
    }

    /**
     * 获取设备操作步骤参数详细信息
     *
     * @param eospaId 主键
     */
    @SaCheckPermission("system:equipmentOperationStepParam:query")
    @GetMapping("/{eospaId}")
    public R<IcesEquipmentOperationStepParamVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long eospaId) {
        return R.ok(iIcesEquipmentOperationStepParamService.queryById(eospaId));
    }

    /**
     * 新增设备操作步骤参数
     */
    @SaCheckPermission("system:equipmentOperationStepParam:add")
    @Log(title = "设备操作步骤参数", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesEquipmentOperationStepParamBo bo) {
        return toAjax(iIcesEquipmentOperationStepParamService.insertByBo(bo));
    }

    /**
     * 修改设备操作步骤参数
     */
    @SaCheckPermission("system:equipmentOperationStepParam:edit")
    @Log(title = "设备操作步骤参数", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesEquipmentOperationStepParamBo bo) {
        return toAjax(iIcesEquipmentOperationStepParamService.updateByBo(bo));
    }

    /**
     * 删除设备操作步骤参数
     *
     * @param eospaIds 主键串
     */
    @SaCheckPermission("system:equipmentOperationStepParam:remove")
    @Log(title = "设备操作步骤参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eospaIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] eospaIds) {
        return toAjax(iIcesEquipmentOperationStepParamService.deleteWithValidByIds(Arrays.asList(eospaIds), true));
    }
}
