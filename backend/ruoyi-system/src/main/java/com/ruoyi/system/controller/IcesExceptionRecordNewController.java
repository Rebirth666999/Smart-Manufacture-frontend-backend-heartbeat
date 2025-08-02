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
import com.ruoyi.system.domain.vo.IcesExceptionRecordNewVo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordNewBo;
import com.ruoyi.system.service.IIcesExceptionRecordNewService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常记录（新）
 *
 * @author ruoyi
 * @date 2025-07-30
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionRecordNew")
public class IcesExceptionRecordNewController extends BaseController {

    private final IIcesExceptionRecordNewService iIcesExceptionRecordNewService;

    /**
     * 查询异常记录（新）列表
     */
    @SaCheckPermission("system:exceptionRecordNew:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionRecordNewVo> list(IcesExceptionRecordNewBo bo, PageQuery pageQuery) {
        return iIcesExceptionRecordNewService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常记录（新）列表
     */
    @SaCheckPermission("system:exceptionRecordNew:export")
    @Log(title = "异常记录（新）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionRecordNewBo bo, HttpServletResponse response) {
        List<IcesExceptionRecordNewVo> list = iIcesExceptionRecordNewService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常记录（新）", IcesExceptionRecordNewVo.class, response);
    }

    /**
     * 获取异常记录（新）详细信息
     *
     * @param exrId 主键
     */
    @SaCheckPermission("system:exceptionRecordNew:query")
    @GetMapping("/{exrId}")
    public R<IcesExceptionRecordNewVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exrId) {
        return R.ok(iIcesExceptionRecordNewService.queryById(exrId));
    }

    /**
     * 新增异常记录（新）
     */
    @SaCheckPermission("system:exceptionRecordNew:add")
    @Log(title = "异常记录（新）", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionRecordNewBo bo) {
        return toAjax(iIcesExceptionRecordNewService.insertByBo(bo));
    }

    /**
     * 修改异常记录（新）
     */
    @SaCheckPermission("system:exceptionRecordNew:edit")
    @Log(title = "异常记录（新）", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionRecordNewBo bo) {
        return toAjax(iIcesExceptionRecordNewService.updateByBo(bo));
    }

    /**
     * 删除异常记录（新）
     *
     * @param exrIds 主键串
     */
    @SaCheckPermission("system:exceptionRecordNew:remove")
    @Log(title = "异常记录（新）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exrIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exrIds) {
        return toAjax(iIcesExceptionRecordNewService.deleteWithValidByIds(Arrays.asList(exrIds), true));
    }
}
