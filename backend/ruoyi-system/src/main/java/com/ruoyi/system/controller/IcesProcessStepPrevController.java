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
import com.ruoyi.system.domain.vo.IcesProcessStepPrevVo;
import com.ruoyi.system.domain.bo.IcesProcessStepPrevBo;
import com.ruoyi.system.service.IIcesProcessStepPrevService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 关联-工艺步骤的前序步骤
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/processStepPrev")
public class IcesProcessStepPrevController extends BaseController {

    private final IIcesProcessStepPrevService iIcesProcessStepPrevService;

    /**
     * 查询关联-工艺步骤的前序步骤列表
     */
    @SaCheckPermission("system:processStepPrev:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProcessStepPrevVo> list(IcesProcessStepPrevBo bo, PageQuery pageQuery) {
        return iIcesProcessStepPrevService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出关联-工艺步骤的前序步骤列表
     */
    @SaCheckPermission("system:processStepPrev:export")
    @Log(title = "关联-工艺步骤的前序步骤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProcessStepPrevBo bo, HttpServletResponse response) {
        List<IcesProcessStepPrevVo> list = iIcesProcessStepPrevService.queryList(bo);
        ExcelUtil.exportExcel(list, "关联-工艺步骤的前序步骤", IcesProcessStepPrevVo.class, response);
    }

    /**
     * 获取关联-工艺步骤的前序步骤详细信息
     *
     * @param pspId 主键
     */
    @SaCheckPermission("system:processStepPrev:query")
    @GetMapping("/{pspId}")
    public R<IcesProcessStepPrevVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long pspId) {
        return R.ok(iIcesProcessStepPrevService.queryById(pspId));
    }

    /**
     * 新增关联-工艺步骤的前序步骤
     */
    @SaCheckPermission("system:processStepPrev:add")
    @Log(title = "关联-工艺步骤的前序步骤", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProcessStepPrevBo bo) {
        return toAjax(iIcesProcessStepPrevService.insertByBo(bo));
    }

    /**
     * 修改关联-工艺步骤的前序步骤
     */
    @SaCheckPermission("system:processStepPrev:edit")
    @Log(title = "关联-工艺步骤的前序步骤", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProcessStepPrevBo bo) {
        return toAjax(iIcesProcessStepPrevService.updateByBo(bo));
    }

    /**
     * 删除关联-工艺步骤的前序步骤
     *
     * @param pspIds 主键串
     */
    @SaCheckPermission("system:processStepPrev:remove")
    @Log(title = "关联-工艺步骤的前序步骤", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pspIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] pspIds) {
        return toAjax(iIcesProcessStepPrevService.deleteWithValidByIds(Arrays.asList(pspIds), true));
    }
}
