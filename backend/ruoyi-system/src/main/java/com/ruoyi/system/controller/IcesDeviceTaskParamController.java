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
import com.ruoyi.system.domain.vo.IcesDeviceTaskParamVo;
import com.ruoyi.system.domain.bo.IcesDeviceTaskParamBo;
import com.ruoyi.system.service.IIcesDeviceTaskParamService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备任务参数
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/deviceTaskParam")
public class IcesDeviceTaskParamController extends BaseController {

    private final IIcesDeviceTaskParamService iIcesDeviceTaskParamService;

    /**
     * 查询设备任务参数列表
     */
    @SaCheckPermission("system:deviceTaskParam:list")
    @GetMapping("/list")
    public TableDataInfo<IcesDeviceTaskParamVo> list(IcesDeviceTaskParamBo bo, PageQuery pageQuery) {
        return iIcesDeviceTaskParamService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备任务参数列表
     */
    @SaCheckPermission("system:deviceTaskParam:export")
    @Log(title = "设备任务参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesDeviceTaskParamBo bo, HttpServletResponse response) {
        List<IcesDeviceTaskParamVo> list = iIcesDeviceTaskParamService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备任务参数", IcesDeviceTaskParamVo.class, response);
    }

    /**
     * 获取设备任务参数详细信息
     *
     * @param dtpaId 主键
     */
    @SaCheckPermission("system:deviceTaskParam:query")
    @GetMapping("/{dtpaId}")
    public R<IcesDeviceTaskParamVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long dtpaId) {
        return R.ok(iIcesDeviceTaskParamService.queryById(dtpaId));
    }

    /**
     * 新增设备任务参数
     */
    @SaCheckPermission("system:deviceTaskParam:add")
    @Log(title = "设备任务参数", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesDeviceTaskParamBo bo) {
        return toAjax(iIcesDeviceTaskParamService.insertByBo(bo));
    }

    /**
     * 修改设备任务参数
     */
    @SaCheckPermission("system:deviceTaskParam:edit")
    @Log(title = "设备任务参数", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesDeviceTaskParamBo bo) {
        return toAjax(iIcesDeviceTaskParamService.updateByBo(bo));
    }

    /**
     * 删除设备任务参数
     *
     * @param dtpaIds 主键串
     */
    @SaCheckPermission("system:deviceTaskParam:remove")
    @Log(title = "设备任务参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dtpaIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] dtpaIds) {
        return toAjax(iIcesDeviceTaskParamService.deleteWithValidByIds(Arrays.asList(dtpaIds), true));
    }
}
