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
import com.ruoyi.system.domain.vo.IcesExceptionRecordVo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordBo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordAiBo;
import com.ruoyi.system.service.IIcesExceptionRecordService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常记录
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionRecord")
public class IcesExceptionRecordController extends BaseController {

    private final IIcesExceptionRecordService iIcesExceptionRecordService;

    /**
     * 查询异常记录列表
     */
    @SaCheckPermission("system:exceptionRecord:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionRecordVo> list(IcesExceptionRecordBo bo, PageQuery pageQuery) {
        return iIcesExceptionRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常记录列表
     */
    @SaCheckPermission("system:exceptionRecord:export")
    @Log(title = "异常记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionRecordBo bo, HttpServletResponse response) {
        List<IcesExceptionRecordVo> list = iIcesExceptionRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常记录", IcesExceptionRecordVo.class, response);
    }

    /**
     * 获取异常记录详细信息
     *
     * @param exrId 主键
     */
    @SaCheckPermission("system:exceptionRecord:query")
    @GetMapping("/{exrId}")
    public R<IcesExceptionRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exrId) {
        return R.ok(iIcesExceptionRecordService.queryById(exrId));
    }

    /**
     * 新增异常记录
     */
    @SaCheckPermission("system:exceptionRecord:add")
    @Log(title = "异常记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<IcesExceptionRecordVo> add(@Validated(AddGroup.class) @RequestBody IcesExceptionRecordBo bo) {
        return R.ok(iIcesExceptionRecordService.insertByBo(bo));
    }

    /**
     * 新增AI异常记录!!!!!!!!!!
     */
    //@SaCheckPermission("system:exceptionRecord:add")
    @Log(title = "异常记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/ai")
    public R<IcesExceptionRecordVo> addAi(@Validated(AddGroup.class) @RequestBody IcesExceptionRecordAiBo bo) {
        return R.ok(iIcesExceptionRecordService.insertByBo(bo));
    }

    /**
     * 修改异常记录
     */
    @SaCheckPermission("system:exceptionRecord:edit")
    @Log(title = "异常记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionRecordBo bo) {
        return toAjax(iIcesExceptionRecordService.updateByBo(bo));
    }

    /**
     * 删除异常记录
     *
     * @param exrIds 主键串
     */
    @SaCheckPermission("system:exceptionRecord:remove")
    @Log(title = "异常记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exrIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exrIds) {
        return toAjax(iIcesExceptionRecordService.deleteWithValidByIds(Arrays.asList(exrIds), true));
    }
}
