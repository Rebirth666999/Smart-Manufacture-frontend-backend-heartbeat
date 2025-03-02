package com.ruoyi.system.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.system.domain.IcesEquipmentRecord;
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
import com.ruoyi.system.domain.vo.IcesEquipmentRecordVo;
import com.ruoyi.system.domain.bo.IcesEquipmentRecordBo;
import com.ruoyi.system.service.IIcesEquipmentRecordService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备事件日志
 *
 * @author ruoyi
 * @date 2025-03-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/equipmentRecord")
public class IcesEquipmentRecordController extends BaseController {

    private final IIcesEquipmentRecordService iIcesEquipmentRecordService;

    /**
     * 查询设备事件日志列表
     */
    @SaCheckPermission("system:equipmentRecord:list")
    @GetMapping("/list")
    public TableDataInfo<IcesEquipmentRecordVo> list(IcesEquipmentRecordBo bo, PageQuery pageQuery) {
        return iIcesEquipmentRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备事件日志列表
     */
    @SaCheckPermission("system:equipmentRecord:export")
    @Log(title = "设备事件日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesEquipmentRecordBo bo, HttpServletResponse response) {
        List<IcesEquipmentRecordVo> list = iIcesEquipmentRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备事件日志", IcesEquipmentRecordVo.class, response);
    }

    /**
     * 获取设备事件日志详细信息
     *
     * @param erId 主键
     */
    @SaCheckPermission("system:equipmentRecord:query")
    @GetMapping("/{erId}")
    public R<IcesEquipmentRecord> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long erId) {
        return R.ok(iIcesEquipmentRecordService.queryById(erId));
    }

    /**
     * 新增设备事件日志
     */
    @SaCheckPermission("system:equipmentRecord:add")
    @Log(title = "设备事件日志", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesEquipmentRecordBo bo) {
        return toAjax(iIcesEquipmentRecordService.insertByBo(bo));
    }

    /**
     * 修改设备事件日志
     */
    @SaCheckPermission("system:equipmentRecord:edit")
    @Log(title = "设备事件日志", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesEquipmentRecordBo bo) {
        return toAjax(iIcesEquipmentRecordService.updateByBo(bo));
    }

    /**
     * 删除设备事件日志
     *
     * @param erIds 主键串
     */
    @SaCheckPermission("system:equipmentRecord:remove")
    @Log(title = "设备事件日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{erIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] erIds) {
        return toAjax(iIcesEquipmentRecordService.deleteWithValidByIds(Arrays.asList(erIds), true));
    }
}
