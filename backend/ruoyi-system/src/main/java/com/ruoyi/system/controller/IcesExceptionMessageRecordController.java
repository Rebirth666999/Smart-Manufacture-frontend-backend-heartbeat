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
import com.ruoyi.system.domain.vo.IcesExceptionMessageRecordVo;
import com.ruoyi.system.domain.bo.IcesExceptionMessageRecordBo;
import com.ruoyi.system.service.IIcesExceptionMessageRecordService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常消息记录
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionMessageRecord")
public class IcesExceptionMessageRecordController extends BaseController {

    private final IIcesExceptionMessageRecordService iIcesExceptionMessageRecordService;

    /**
     * 查询异常消息记录列表
     */
    @SaCheckPermission("system:exceptionMessageRecord:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionMessageRecordVo> list(IcesExceptionMessageRecordBo bo, PageQuery pageQuery) {
        return iIcesExceptionMessageRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常消息记录列表
     */
    @SaCheckPermission("system:exceptionMessageRecord:export")
    @Log(title = "异常消息记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionMessageRecordBo bo, HttpServletResponse response) {
        List<IcesExceptionMessageRecordVo> list = iIcesExceptionMessageRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常消息记录", IcesExceptionMessageRecordVo.class, response);
    }

    /**
     * 获取异常消息记录详细信息
     *
     * @param exmrId 主键
     */
    @SaCheckPermission("system:exceptionMessageRecord:query")
    @GetMapping("/{exmrId}")
    public R<IcesExceptionMessageRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exmrId) {
        return R.ok(iIcesExceptionMessageRecordService.queryById(exmrId));
    }

    /**
     * 新增异常消息记录
     */
    @SaCheckPermission("system:exceptionMessageRecord:add")
    @Log(title = "异常消息记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionMessageRecordBo bo) {
        return toAjax(iIcesExceptionMessageRecordService.insertByBo(bo));
    }

    /**
     * 修改异常消息记录
     */
    @SaCheckPermission("system:exceptionMessageRecord:edit")
    @Log(title = "异常消息记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionMessageRecordBo bo) {
        return toAjax(iIcesExceptionMessageRecordService.updateByBo(bo));
    }

    /**
     * 删除异常消息记录
     *
     * @param exmrIds 主键串
     */
    @SaCheckPermission("system:exceptionMessageRecord:remove")
    @Log(title = "异常消息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exmrIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exmrIds) {
        return toAjax(iIcesExceptionMessageRecordService.deleteWithValidByIds(Arrays.asList(exmrIds), true));
    }
}
