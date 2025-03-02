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
import com.ruoyi.system.domain.vo.IcesEquipmentAtomOperationVo;
import com.ruoyi.system.domain.bo.IcesEquipmentAtomOperationBo;
import com.ruoyi.system.service.IIcesEquipmentAtomOperationService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备原子操作
 *
 * @author ruoyi
 * @date 2025-03-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/equipmentAtomOperation")
public class IcesEquipmentAtomOperationController extends BaseController {

    private final IIcesEquipmentAtomOperationService iIcesEquipmentAtomOperationService;

    /**
     * 查询设备原子操作列表
     */
    @SaCheckPermission("system:equipmentAtomOperation:list")
    @GetMapping("/list")
    public TableDataInfo<IcesEquipmentAtomOperationVo> list(IcesEquipmentAtomOperationBo bo, PageQuery pageQuery) {
        return iIcesEquipmentAtomOperationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备原子操作列表
     */
    @SaCheckPermission("system:equipmentAtomOperation:export")
    @Log(title = "设备原子操作", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesEquipmentAtomOperationBo bo, HttpServletResponse response) {
        List<IcesEquipmentAtomOperationVo> list = iIcesEquipmentAtomOperationService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备原子操作", IcesEquipmentAtomOperationVo.class, response);
    }

    /**
     * 获取设备原子操作详细信息
     *
     * @param eaoId 主键
     */
    @SaCheckPermission("system:equipmentAtomOperation:query")
    @GetMapping("/{eaoId}")
    public R<IcesEquipmentAtomOperationVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long eaoId) {
        return R.ok(iIcesEquipmentAtomOperationService.queryById(eaoId));
    }

    /**
     * 新增设备原子操作
     */
    @SaCheckPermission("system:equipmentAtomOperation:add")
    @Log(title = "设备原子操作", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesEquipmentAtomOperationBo bo) {
        return toAjax(iIcesEquipmentAtomOperationService.insertByBo(bo));
    }

    /**
     * 修改设备原子操作
     */
    @SaCheckPermission("system:equipmentAtomOperation:edit")
    @Log(title = "设备原子操作", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesEquipmentAtomOperationBo bo) {
        return toAjax(iIcesEquipmentAtomOperationService.updateByBo(bo));
    }

    /**
     * 删除设备原子操作
     *
     * @param eaoIds 主键串
     */
    @SaCheckPermission("system:equipmentAtomOperation:remove")
    @Log(title = "设备原子操作", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eaoIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] eaoIds) {
        return toAjax(iIcesEquipmentAtomOperationService.deleteWithValidByIds(Arrays.asList(eaoIds), true));
    }
}
