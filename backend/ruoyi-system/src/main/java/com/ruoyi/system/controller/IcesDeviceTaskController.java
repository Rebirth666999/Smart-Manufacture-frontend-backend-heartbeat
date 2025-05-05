package com.ruoyi.system.controller;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.system.domain.bo.IcesManufactureTaskBo;
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
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.IcesDeviceTaskVo;
import com.ruoyi.system.domain.bo.IcesDeviceTaskBo;
import com.ruoyi.system.service.IIcesDeviceTaskService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备任务
 *
 * @author ruoyi
 * @date 2025-03-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/deviceTask")
public class IcesDeviceTaskController extends BaseController {

    private final IIcesDeviceTaskService iIcesDeviceTaskService;


    /**
     * 查询设备任务列表
     */
    @SaCheckPermission("system:deviceTask:list")
    @GetMapping("/list")
    public TableDataInfo<IcesDeviceTaskVo> list(IcesDeviceTaskBo bo, PageQuery pageQuery) {
        return iIcesDeviceTaskService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备任务列表
     */
    @SaCheckPermission("system:deviceTask:export")
    @Log(title = "设备任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesDeviceTaskBo bo, HttpServletResponse response) {
        List<IcesDeviceTaskVo> list = iIcesDeviceTaskService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备任务", IcesDeviceTaskVo.class, response);
    }

    /**
     * 获取设备任务详细信息
     *
     * @param dtId 主键
     */
    @SaCheckPermission("system:deviceTask:query")
    @GetMapping("/{dtId}")
    public R<IcesDeviceTaskVo> getInfo(@NotNull(message = "主键不能为空")
                                       @PathVariable Long dtId) {
        return R.ok(iIcesDeviceTaskService.queryById(dtId));
    }

    /**
     * 新增设备任务
     */
    @SaCheckPermission("system:deviceTask:add")
    @Log(title = "设备任务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesDeviceTaskBo bo) {
        return toAjax(iIcesDeviceTaskService.insertByBo(bo));
    }

    /**
     * 修改设备任务
     */
    @SaCheckPermission("system:deviceTask:edit")
    @Log(title = "设备任务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesDeviceTaskBo bo) {
        return toAjax(iIcesDeviceTaskService.updateByBo(bo));
    }

    /**
     * 删除设备任务
     *
     * @param dtIds 主键串
     */
    @SaCheckPermission("system:deviceTask:remove")
    @Log(title = "设备任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dtIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] dtIds) {
        return toAjax(iIcesDeviceTaskService.deleteWithValidByIds(Arrays.asList(dtIds), true));
    }

    /**
     * 从生产任务下发设备任务
     * @param jsonStr 请求体JSON
     */
    @Log(title = "设备任务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/saveTasks")
    public R<Void> saveDeviceTasks(@RequestBody String jsonStr) {
        try {
            iIcesDeviceTaskService.saveDtasks(jsonStr);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail();
        }
    }

    /**
     * 按生产任务获取本轮剩余设备任务
     * @param manufactureTaskBo 要查询的生产任务
     */
    @RepeatSubmit()
    @PostMapping("/findRemainByManufactureTask")
    public R<List<IcesDeviceTaskVo>> findRemainByManufactureTask(@RequestBody IcesManufactureTaskBo manufactureTaskBo) {
        return R.ok(iIcesDeviceTaskService.findRemain(manufactureTaskBo.getMtCode()));
    }

}



