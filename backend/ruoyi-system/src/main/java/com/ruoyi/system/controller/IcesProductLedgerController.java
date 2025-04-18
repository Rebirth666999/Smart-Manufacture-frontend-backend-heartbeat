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
import com.ruoyi.system.domain.vo.IcesProductLedgerVo;
import com.ruoyi.system.domain.bo.IcesProductLedgerBo;
import com.ruoyi.system.service.IIcesProductLedgerService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品台账
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/productLedger")
public class IcesProductLedgerController extends BaseController {

    private final IIcesProductLedgerService iIcesProductLedgerService;

    /**
     * 查询产品台账列表
     */
    @SaCheckPermission("system:productLedger:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProductLedgerVo> list(IcesProductLedgerBo bo, PageQuery pageQuery) {
        return iIcesProductLedgerService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出产品台账列表
     */
    @SaCheckPermission("system:productLedger:export")
    @Log(title = "产品台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProductLedgerBo bo, HttpServletResponse response) {
        List<IcesProductLedgerVo> list = iIcesProductLedgerService.queryList(bo);
        ExcelUtil.exportExcel(list, "产品台账", IcesProductLedgerVo.class, response);
    }

    /**
     * 获取产品台账详细信息
     *
     * @param plId 主键
     */
    @SaCheckPermission("system:productLedger:query")
    @GetMapping("/{plId}")
    public R<IcesProductLedgerVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long plId) {
        return R.ok(iIcesProductLedgerService.queryById(plId));
    }

    /**
     * 新增产品台账
     */
    @SaCheckPermission("system:productLedger:add")
    @Log(title = "产品台账", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProductLedgerBo bo) {
        return toAjax(iIcesProductLedgerService.insertByBo(bo));
    }

    /**
     * 修改产品台账
     */
    @SaCheckPermission("system:productLedger:edit")
    @Log(title = "产品台账", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProductLedgerBo bo) {
        return toAjax(iIcesProductLedgerService.updateByBo(bo));
    }

    /**
     * 删除产品台账
     *
     * @param plIds 主键串
     */
    @SaCheckPermission("system:productLedger:remove")
    @Log(title = "产品台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{plIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] plIds) {
        return toAjax(iIcesProductLedgerService.deleteWithValidByIds(Arrays.asList(plIds), true));
    }
}
