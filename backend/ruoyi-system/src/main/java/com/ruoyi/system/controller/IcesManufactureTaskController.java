package com.ruoyi.system.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
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
import com.ruoyi.system.domain.vo.IcesManufactureTaskVo;
import com.ruoyi.system.domain.bo.IcesManufactureTaskBo;
import com.ruoyi.system.service.IIcesManufactureTaskService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生产任务
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/manufactureTask")
public class IcesManufactureTaskController extends BaseController {

    private final IIcesManufactureTaskService iIcesManufactureTaskService;

    /**
     * 查询生产任务列表
     */
    @SaCheckPermission("system:manufactureTask:list")
    @GetMapping("/list")
    public TableDataInfo<IcesManufactureTaskVo> list(IcesManufactureTaskBo bo, PageQuery pageQuery) {
        return iIcesManufactureTaskService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出生产任务列表
     */
    @SaCheckPermission("system:manufactureTask:export")
    @Log(title = "生产任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesManufactureTaskBo bo, HttpServletResponse response) {
        List<IcesManufactureTaskVo> list = iIcesManufactureTaskService.queryList(bo);
        ExcelUtil.exportExcel(list, "生产任务", IcesManufactureTaskVo.class, response);
    }

    /**
     * 获取生产任务详细信息
     *
     * @param mtId 主键
     */
    @SaCheckPermission("system:manufactureTask:query")
    @GetMapping("/{mtId}")
    public R<IcesManufactureTaskVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long mtId) {
        return R.ok(iIcesManufactureTaskService.queryById(mtId));
    }

    /**
     * 新增生产任务
     */
    @SaCheckPermission("system:manufactureTask:add")
    @Log(title = "生产任务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesManufactureTaskBo bo) {
        return toAjax(iIcesManufactureTaskService.insertByBo(bo));
    }

    /**
     * 修改生产任务
     */
    @SaCheckPermission("system:manufactureTask:edit")
    @Log(title = "生产任务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesManufactureTaskBo bo) {
        return toAjax(iIcesManufactureTaskService.updateByBo(bo));
    }

    /**
     * 删除生产任务
     *
     * @param mtIds 主键串
     */
    @SaCheckPermission("system:manufactureTask:remove")
    @Log(title = "生产任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{mtIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] mtIds) {
        return toAjax(iIcesManufactureTaskService.deleteWithValidByIds(Arrays.asList(mtIds), true));
    }
}
