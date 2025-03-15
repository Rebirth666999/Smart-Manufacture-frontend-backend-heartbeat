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
import com.ruoyi.system.domain.vo.IcesProcessMaterialVo;
import com.ruoyi.system.domain.bo.IcesProcessMaterialBo;
import com.ruoyi.system.service.IIcesProcessMaterialService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 关联-工艺流程原料需求
 *
 * @author ruoyi
 * @date 2025-03-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/processMaterial")
public class IcesProcessMaterialController extends BaseController {

    private final IIcesProcessMaterialService iIcesProcessMaterialService;

    /**
     * 查询关联-工艺流程原料需求列表
     */
    @SaCheckPermission("system:processMaterial:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProcessMaterialVo> list(IcesProcessMaterialBo bo, PageQuery pageQuery) {
        return iIcesProcessMaterialService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出关联-工艺流程原料需求列表
     */
    @SaCheckPermission("system:processMaterial:export")
    @Log(title = "关联-工艺流程原料需求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProcessMaterialBo bo, HttpServletResponse response) {
        List<IcesProcessMaterialVo> list = iIcesProcessMaterialService.queryList(bo);
        ExcelUtil.exportExcel(list, "关联-工艺流程原料需求", IcesProcessMaterialVo.class, response);
    }

    /**
     * 获取关联-工艺流程原料需求详细信息
     *
     * @param pmId 主键
     */
    @SaCheckPermission("system:processMaterial:query")
    @GetMapping("/{pmId}")
    public R<IcesProcessMaterialVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long pmId) {
        return R.ok(iIcesProcessMaterialService.queryById(pmId));
    }

    /**
     * 新增关联-工艺流程原料需求
     */
    @SaCheckPermission("system:processMaterial:add")
    @Log(title = "关联-工艺流程原料需求", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProcessMaterialBo bo) {
        return toAjax(iIcesProcessMaterialService.insertByBo(bo));
    }

    /**
     * 修改关联-工艺流程原料需求
     */
    @SaCheckPermission("system:processMaterial:edit")
    @Log(title = "关联-工艺流程原料需求", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProcessMaterialBo bo) {
        return toAjax(iIcesProcessMaterialService.updateByBo(bo));
    }

    /**
     * 删除关联-工艺流程原料需求
     *
     * @param pmIds 主键串
     */
    @SaCheckPermission("system:processMaterial:remove")
    @Log(title = "关联-工艺流程原料需求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pmIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] pmIds) {
        return toAjax(iIcesProcessMaterialService.deleteWithValidByIds(Arrays.asList(pmIds), true));
    }
}
