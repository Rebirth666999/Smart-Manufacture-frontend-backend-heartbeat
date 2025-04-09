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
import com.ruoyi.system.domain.vo.IcesExceptionRecordLogVo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordLogBo;
import com.ruoyi.system.service.IIcesExceptionRecordLogService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常处理日志
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionRecordLog")
public class IcesExceptionRecordLogController extends BaseController {

    private final IIcesExceptionRecordLogService iIcesExceptionRecordLogService;

    /**
     * 查询异常处理日志列表
     */
    @SaCheckPermission("system:exceptionRecordLog:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionRecordLogVo> list(IcesExceptionRecordLogBo bo, PageQuery pageQuery) {
        return iIcesExceptionRecordLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常处理日志列表
     */
    @SaCheckPermission("system:exceptionRecordLog:export")
    @Log(title = "异常处理日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionRecordLogBo bo, HttpServletResponse response) {
        List<IcesExceptionRecordLogVo> list = iIcesExceptionRecordLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常处理日志", IcesExceptionRecordLogVo.class, response);
    }

    /**
     * 获取异常处理日志详细信息
     *
     * @param exrlId 主键
     */
    @SaCheckPermission("system:exceptionRecordLog:query")
    @GetMapping("/{exrlId}")
    public R<IcesExceptionRecordLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exrlId) {
        return R.ok(iIcesExceptionRecordLogService.queryById(exrlId));
    }

    /**
     * 新增异常处理日志
     */
    @SaCheckPermission("system:exceptionRecordLog:add")
    @Log(title = "异常处理日志", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionRecordLogBo bo) {
        return toAjax(iIcesExceptionRecordLogService.insertByBo(bo));
    }

    /**
     * 修改异常处理日志
     */
    @SaCheckPermission("system:exceptionRecordLog:edit")
    @Log(title = "异常处理日志", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionRecordLogBo bo) {
        return toAjax(iIcesExceptionRecordLogService.updateByBo(bo));
    }

    /**
     * 删除异常处理日志
     *
     * @param exrlIds 主键串
     */
    @SaCheckPermission("system:exceptionRecordLog:remove")
    @Log(title = "异常处理日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exrlIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exrlIds) {
        return toAjax(iIcesExceptionRecordLogService.deleteWithValidByIds(Arrays.asList(exrlIds), true));
    }
}
