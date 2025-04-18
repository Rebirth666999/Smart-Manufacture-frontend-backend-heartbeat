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
import com.ruoyi.system.domain.vo.IcesMaterialLedgerVo;
import com.ruoyi.system.domain.bo.IcesMaterialLedgerBo;
import com.ruoyi.system.service.IIcesMaterialLedgerService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 原料台账
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/materialLedger")
public class IcesMaterialLedgerController extends BaseController {

    private final IIcesMaterialLedgerService iIcesMaterialLedgerService;

    /**
     * 查询原料台账列表
     */
    @SaCheckPermission("system:materialLedger:list")
    @GetMapping("/list")
    public TableDataInfo<IcesMaterialLedgerVo> list(IcesMaterialLedgerBo bo, PageQuery pageQuery) {
        return iIcesMaterialLedgerService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出原料台账列表
     */
    @SaCheckPermission("system:materialLedger:export")
    @Log(title = "原料台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesMaterialLedgerBo bo, HttpServletResponse response) {
        List<IcesMaterialLedgerVo> list = iIcesMaterialLedgerService.queryList(bo);
        ExcelUtil.exportExcel(list, "原料台账", IcesMaterialLedgerVo.class, response);
    }

    /**
     * 获取原料台账详细信息
     *
     * @param mlId 主键
     */
    @SaCheckPermission("system:materialLedger:query")
    @GetMapping("/{mlId}")
    public R<IcesMaterialLedgerVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long mlId) {
        return R.ok(iIcesMaterialLedgerService.queryById(mlId));
    }

    /**
     * 新增原料台账
     */
    @SaCheckPermission("system:materialLedger:add")
    @Log(title = "原料台账", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesMaterialLedgerBo bo) {
        return toAjax(iIcesMaterialLedgerService.insertByBo(bo));
    }

    /**
     * 修改原料台账
     */
    @SaCheckPermission("system:materialLedger:edit")
    @Log(title = "原料台账", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesMaterialLedgerBo bo) {
        return toAjax(iIcesMaterialLedgerService.updateByBo(bo));
    }

    /**
     * 删除原料台账
     *
     * @param mlIds 主键串
     */
    @SaCheckPermission("system:materialLedger:remove")
    @Log(title = "原料台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{mlIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] mlIds) {
        return toAjax(iIcesMaterialLedgerService.deleteWithValidByIds(Arrays.asList(mlIds), true));
    }
}
