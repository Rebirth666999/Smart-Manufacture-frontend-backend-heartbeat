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
import com.ruoyi.system.domain.vo.IcesProductBatchVo;
import com.ruoyi.system.domain.bo.IcesProductBatchBo;
import com.ruoyi.system.service.IIcesProductBatchService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 实际产品批次编码
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/productBatch")
public class IcesProductBatchController extends BaseController {

    private final IIcesProductBatchService iIcesProductBatchService;

    /**
     * 查询实际产品批次编码列表
     */
    @SaCheckPermission("system:productBatch:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProductBatchVo> list(IcesProductBatchBo bo, PageQuery pageQuery) {
        return iIcesProductBatchService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出实际产品批次编码列表
     */
    @SaCheckPermission("system:productBatch:export")
    @Log(title = "实际产品批次编码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProductBatchBo bo, HttpServletResponse response) {
        List<IcesProductBatchVo> list = iIcesProductBatchService.queryList(bo);
        ExcelUtil.exportExcel(list, "实际产品批次编码", IcesProductBatchVo.class, response);
    }

    /**
     * 获取实际产品批次编码详细信息
     *
     * @param pbId 主键
     */
    @SaCheckPermission("system:productBatch:query")
    @GetMapping("/{pbId}")
    public R<IcesProductBatchVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long pbId) {
        return R.ok(iIcesProductBatchService.queryById(pbId));
    }

    /**
     * 新增实际产品批次编码
     */
    @SaCheckPermission("system:productBatch:add")
    @Log(title = "实际产品批次编码", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProductBatchBo bo) {
        return toAjax(iIcesProductBatchService.insertByBo(bo));
    }

    /**
     * 修改实际产品批次编码
     */
    @SaCheckPermission("system:productBatch:edit")
    @Log(title = "实际产品批次编码", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProductBatchBo bo) {
        return toAjax(iIcesProductBatchService.updateByBo(bo));
    }

    /**
     * 删除实际产品批次编码
     *
     * @param pbIds 主键串
     */
    @SaCheckPermission("system:productBatch:remove")
    @Log(title = "实际产品批次编码", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pbIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] pbIds) {
        return toAjax(iIcesProductBatchService.deleteWithValidByIds(Arrays.asList(pbIds), true));
    }
}
