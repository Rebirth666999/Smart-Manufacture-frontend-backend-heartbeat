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
import com.ruoyi.system.domain.vo.IcesProcessStepVo;
import com.ruoyi.system.domain.bo.IcesProcessStepBo;
import com.ruoyi.system.service.IIcesProcessStepService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺步骤
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/processStep")
public class IcesProcessStepController extends BaseController {

    private final IIcesProcessStepService iIcesProcessStepService;

    /**
     * 查询工艺步骤列表
     */
    @SaCheckPermission("system:processStep:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProcessStepVo> list(IcesProcessStepBo bo, PageQuery pageQuery) {
        return iIcesProcessStepService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出工艺步骤列表
     */
    @SaCheckPermission("system:processStep:export")
    @Log(title = "工艺步骤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProcessStepBo bo, HttpServletResponse response) {
        List<IcesProcessStepVo> list = iIcesProcessStepService.queryList(bo);
        ExcelUtil.exportExcel(list, "工艺步骤", IcesProcessStepVo.class, response);
    }

    /**
     * 获取工艺步骤详细信息
     *
     * @param psId 主键
     */
    @SaCheckPermission("system:processStep:query")
    @GetMapping("/{psId}")
    public R<IcesProcessStepVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long psId) {
        return R.ok(iIcesProcessStepService.queryById(psId));
    }

    /**
     * 新增工艺步骤
     */
    @SaCheckPermission("system:processStep:add")
    @Log(title = "工艺步骤", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProcessStepBo bo) {
        return toAjax(iIcesProcessStepService.insertByBo(bo));
    }

    /**
     * 修改工艺步骤
     */
    @SaCheckPermission("system:processStep:edit")
    @Log(title = "工艺步骤", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProcessStepBo bo) {
        return toAjax(iIcesProcessStepService.updateByBo(bo));
    }

    /**
     * 删除工艺步骤
     *
     * @param psIds 主键串
     */
    @SaCheckPermission("system:processStep:remove")
    @Log(title = "工艺步骤", businessType = BusinessType.DELETE)
    @DeleteMapping("/{psIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] psIds) {
        return toAjax(iIcesProcessStepService.deleteWithValidByIds(Arrays.asList(psIds), true));
    }
}
