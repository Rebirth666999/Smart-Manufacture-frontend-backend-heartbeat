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
import com.ruoyi.system.domain.vo.IcesMaterialRecordVo;
import com.ruoyi.system.domain.bo.IcesMaterialRecordBo;
import com.ruoyi.system.service.IIcesMaterialRecordService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 原料台账
 *
 * @author ruoyi
 * @date 2025-02-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/materialRecord")
public class IcesMaterialRecordController extends BaseController {

    private final IIcesMaterialRecordService iIcesMaterialRecordService;

    /**
     * 查询原料台账列表
     */
    @SaCheckPermission("system:materialRecord:list")
    @GetMapping("/list")
    public TableDataInfo<IcesMaterialRecordVo> list(IcesMaterialRecordBo bo, PageQuery pageQuery) {
        return iIcesMaterialRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出原料台账列表
     */
    @SaCheckPermission("system:materialRecord:export")
    @Log(title = "原料台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesMaterialRecordBo bo, HttpServletResponse response) {
        List<IcesMaterialRecordVo> list = iIcesMaterialRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "原料台账", IcesMaterialRecordVo.class, response);
    }

    /**
     * 获取原料台账详细信息
     *
     * @param mrId 主键
     */
    @SaCheckPermission("system:materialRecord:query")
    @GetMapping("/{mrId}")
    public R<IcesMaterialRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long mrId) {
        return R.ok(iIcesMaterialRecordService.queryById(mrId));
    }

    /**
     * 新增原料台账
     */
    @SaCheckPermission("system:materialRecord:add")
    @Log(title = "原料台账", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesMaterialRecordBo bo) {
        return toAjax(iIcesMaterialRecordService.insertByBo(bo));
    }

    /**
     * 修改原料台账
     */
    @SaCheckPermission("system:materialRecord:edit")
    @Log(title = "原料台账", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesMaterialRecordBo bo) {
        return toAjax(iIcesMaterialRecordService.updateByBo(bo));
    }

    /**
     * 删除原料台账
     *
     * @param mrIds 主键串
     */
    @SaCheckPermission("system:materialRecord:remove")
    @Log(title = "原料台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{mrIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] mrIds) {
        return toAjax(iIcesMaterialRecordService.deleteWithValidByIds(Arrays.asList(mrIds), true));
    }
}
