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
import com.ruoyi.system.domain.vo.IcesDeviceTaskPrevVo;
import com.ruoyi.system.domain.bo.IcesDeviceTaskPrevBo;
import com.ruoyi.system.service.IIcesDeviceTaskPrevService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 关联-设备任务的前序任务
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/deviceTaskPrev")
public class IcesDeviceTaskPrevController extends BaseController {

    private final IIcesDeviceTaskPrevService iIcesDeviceTaskPrevService;

    /**
     * 查询关联-设备任务的前序任务列表
     */
    @SaCheckPermission("system:deviceTaskPrev:list")
    @GetMapping("/list")
    public TableDataInfo<IcesDeviceTaskPrevVo> list(IcesDeviceTaskPrevBo bo, PageQuery pageQuery) {
        return iIcesDeviceTaskPrevService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出关联-设备任务的前序任务列表
     */
    @SaCheckPermission("system:deviceTaskPrev:export")
    @Log(title = "关联-设备任务的前序任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesDeviceTaskPrevBo bo, HttpServletResponse response) {
        List<IcesDeviceTaskPrevVo> list = iIcesDeviceTaskPrevService.queryList(bo);
        ExcelUtil.exportExcel(list, "关联-设备任务的前序任务", IcesDeviceTaskPrevVo.class, response);
    }

    /**
     * 获取关联-设备任务的前序任务详细信息
     *
     * @param dtprId 主键
     */
    @SaCheckPermission("system:deviceTaskPrev:query")
    @GetMapping("/{dtprId}")
    public R<IcesDeviceTaskPrevVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long dtprId) {
        return R.ok(iIcesDeviceTaskPrevService.queryById(dtprId));
    }

    /**
     * 新增关联-设备任务的前序任务
     */
    @SaCheckPermission("system:deviceTaskPrev:add")
    @Log(title = "关联-设备任务的前序任务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesDeviceTaskPrevBo bo) {
        return toAjax(iIcesDeviceTaskPrevService.insertByBo(bo));
    }

    /**
     * 修改关联-设备任务的前序任务
     */
    @SaCheckPermission("system:deviceTaskPrev:edit")
    @Log(title = "关联-设备任务的前序任务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesDeviceTaskPrevBo bo) {
        return toAjax(iIcesDeviceTaskPrevService.updateByBo(bo));
    }

    /**
     * 删除关联-设备任务的前序任务
     *
     * @param dtprIds 主键串
     */
    @SaCheckPermission("system:deviceTaskPrev:remove")
    @Log(title = "关联-设备任务的前序任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dtprIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] dtprIds) {
        return toAjax(iIcesDeviceTaskPrevService.deleteWithValidByIds(Arrays.asList(dtprIds), true));
    }
}
