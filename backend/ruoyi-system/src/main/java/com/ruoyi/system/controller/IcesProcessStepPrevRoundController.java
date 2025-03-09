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
import com.ruoyi.system.domain.vo.IcesProcessStepPrevRoundVo;
import com.ruoyi.system.domain.bo.IcesProcessStepPrevRoundBo;
import com.ruoyi.system.service.IIcesProcessStepPrevRoundService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 关联-工艺步骤的跨轮次前序步骤
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/processStepPrevRound")
public class IcesProcessStepPrevRoundController extends BaseController {

    private final IIcesProcessStepPrevRoundService iIcesProcessStepPrevRoundService;

    /**
     * 查询关联-工艺步骤的跨轮次前序步骤列表
     */
    @SaCheckPermission("system:processStepPrevRound:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProcessStepPrevRoundVo> list(IcesProcessStepPrevRoundBo bo, PageQuery pageQuery) {
        return iIcesProcessStepPrevRoundService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出关联-工艺步骤的跨轮次前序步骤列表
     */
    @SaCheckPermission("system:processStepPrevRound:export")
    @Log(title = "关联-工艺步骤的跨轮次前序步骤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProcessStepPrevRoundBo bo, HttpServletResponse response) {
        List<IcesProcessStepPrevRoundVo> list = iIcesProcessStepPrevRoundService.queryList(bo);
        ExcelUtil.exportExcel(list, "关联-工艺步骤的跨轮次前序步骤", IcesProcessStepPrevRoundVo.class, response);
    }

    /**
     * 获取关联-工艺步骤的跨轮次前序步骤详细信息
     *
     * @param psprId 主键
     */
    @SaCheckPermission("system:processStepPrevRound:query")
    @GetMapping("/{psprId}")
    public R<IcesProcessStepPrevRoundVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long psprId) {
        return R.ok(iIcesProcessStepPrevRoundService.queryById(psprId));
    }

    /**
     * 新增关联-工艺步骤的跨轮次前序步骤
     */
    @SaCheckPermission("system:processStepPrevRound:add")
    @Log(title = "关联-工艺步骤的跨轮次前序步骤", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProcessStepPrevRoundBo bo) {
        return toAjax(iIcesProcessStepPrevRoundService.insertByBo(bo));
    }

    /**
     * 修改关联-工艺步骤的跨轮次前序步骤
     */
    @SaCheckPermission("system:processStepPrevRound:edit")
    @Log(title = "关联-工艺步骤的跨轮次前序步骤", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProcessStepPrevRoundBo bo) {
        return toAjax(iIcesProcessStepPrevRoundService.updateByBo(bo));
    }

    /**
     * 删除关联-工艺步骤的跨轮次前序步骤
     *
     * @param psprIds 主键串
     */
    @SaCheckPermission("system:processStepPrevRound:remove")
    @Log(title = "关联-工艺步骤的跨轮次前序步骤", businessType = BusinessType.DELETE)
    @DeleteMapping("/{psprIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] psprIds) {
        return toAjax(iIcesProcessStepPrevRoundService.deleteWithValidByIds(Arrays.asList(psprIds), true));
    }
}
