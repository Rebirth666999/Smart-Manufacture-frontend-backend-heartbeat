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
import com.ruoyi.system.domain.vo.IcesEquipmentOperationVo;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationBo;
import com.ruoyi.system.service.IIcesEquipmentOperationService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备操作
 *
 * @author ruoyi
 * @date 2025-03-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/equipmentOperation")
public class IcesEquipmentOperationController extends BaseController {

    private final IIcesEquipmentOperationService iIcesEquipmentOperationService;

    /**
     * 查询设备操作列表
     */
    @SaCheckPermission("system:equipmentOperation:list")
    @GetMapping("/list")
    public TableDataInfo<IcesEquipmentOperationVo> list(IcesEquipmentOperationBo bo, PageQuery pageQuery) {
        return iIcesEquipmentOperationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备操作列表
     */
    @SaCheckPermission("system:equipmentOperation:export")
    @Log(title = "设备操作", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesEquipmentOperationBo bo, HttpServletResponse response) {
        List<IcesEquipmentOperationVo> list = iIcesEquipmentOperationService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备操作", IcesEquipmentOperationVo.class, response);
    }

    /**
     * 获取设备操作详细信息
     *
     * @param eoId 主键
     */
    @SaCheckPermission("system:equipmentOperation:query")
    @GetMapping("/{eoId}")
    public R<IcesEquipmentOperationVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long eoId) {
        return R.ok(iIcesEquipmentOperationService.queryById(eoId));
    }

    /**
     * 新增设备操作
     */
    @SaCheckPermission("system:equipmentOperation:add")
    @Log(title = "设备操作", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesEquipmentOperationBo bo) {
        return toAjax(iIcesEquipmentOperationService.insertByBo(bo));
    }

    /**
     * 修改设备操作
     */
    @SaCheckPermission("system:equipmentOperation:edit")
    @Log(title = "设备操作", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesEquipmentOperationBo bo) {
        return toAjax(iIcesEquipmentOperationService.updateByBo(bo));
    }

    /**
     * 删除设备操作
     *
     * @param eoIds 主键串
     */
    @SaCheckPermission("system:equipmentOperation:remove")
    @Log(title = "设备操作", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eoIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] eoIds) {
        return toAjax(iIcesEquipmentOperationService.deleteWithValidByIds(Arrays.asList(eoIds), true));
    }
}
