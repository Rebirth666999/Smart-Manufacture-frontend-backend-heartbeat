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
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepPrevVo;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationStepPrevBo;
import com.ruoyi.system.service.IIcesEquipmentOperationStepPrevService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 关联-设备操作步骤的前序步骤
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/equipmentOperationStepPrev")
public class IcesEquipmentOperationStepPrevController extends BaseController {

    private final IIcesEquipmentOperationStepPrevService iIcesEquipmentOperationStepPrevService;

    /**
     * 查询关联-设备操作步骤的前序步骤列表
     */
    @SaCheckPermission("system:equipmentOperationStepPrev:list")
    @GetMapping("/list")
    public TableDataInfo<IcesEquipmentOperationStepPrevVo> list(IcesEquipmentOperationStepPrevBo bo, PageQuery pageQuery) {
        return iIcesEquipmentOperationStepPrevService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出关联-设备操作步骤的前序步骤列表
     */
    @SaCheckPermission("system:equipmentOperationStepPrev:export")
    @Log(title = "关联-设备操作步骤的前序步骤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesEquipmentOperationStepPrevBo bo, HttpServletResponse response) {
        List<IcesEquipmentOperationStepPrevVo> list = iIcesEquipmentOperationStepPrevService.queryList(bo);
        ExcelUtil.exportExcel(list, "关联-设备操作步骤的前序步骤", IcesEquipmentOperationStepPrevVo.class, response);
    }

    /**
     * 获取关联-设备操作步骤的前序步骤详细信息
     *
     * @param eosprId 主键
     */
    @SaCheckPermission("system:equipmentOperationStepPrev:query")
    @GetMapping("/{eosprId}")
    public R<IcesEquipmentOperationStepPrevVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long eosprId) {
        return R.ok(iIcesEquipmentOperationStepPrevService.queryById(eosprId));
    }

    /**
     * 新增关联-设备操作步骤的前序步骤
     */
    @SaCheckPermission("system:equipmentOperationStepPrev:add")
    @Log(title = "关联-设备操作步骤的前序步骤", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesEquipmentOperationStepPrevBo bo) {
        return toAjax(iIcesEquipmentOperationStepPrevService.insertByBo(bo));
    }

    /**
     * 修改关联-设备操作步骤的前序步骤
     */
    @SaCheckPermission("system:equipmentOperationStepPrev:edit")
    @Log(title = "关联-设备操作步骤的前序步骤", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesEquipmentOperationStepPrevBo bo) {
        return toAjax(iIcesEquipmentOperationStepPrevService.updateByBo(bo));
    }

    /**
     * 删除关联-设备操作步骤的前序步骤
     *
     * @param eosprIds 主键串
     */
    @SaCheckPermission("system:equipmentOperationStepPrev:remove")
    @Log(title = "关联-设备操作步骤的前序步骤", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eosprIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] eosprIds) {
        return toAjax(iIcesEquipmentOperationStepPrevService.deleteWithValidByIds(Arrays.asList(eosprIds), true));
    }
}
