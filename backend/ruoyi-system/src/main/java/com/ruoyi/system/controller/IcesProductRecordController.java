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
import com.ruoyi.system.domain.vo.IcesProductRecordVo;
import com.ruoyi.system.domain.bo.IcesProductRecordBo;
import com.ruoyi.system.service.IIcesProductRecordService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品台账
 *
 * @author ruoyi
 * @date 2025-02-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/productRecord")
public class IcesProductRecordController extends BaseController {

    private final IIcesProductRecordService iIcesProductRecordService;

    /**
     * 查询产品台账列表
     */
    @SaCheckPermission("system:productRecord:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProductRecordVo> list(IcesProductRecordBo bo, PageQuery pageQuery) {
        return iIcesProductRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出产品台账列表
     */
    @SaCheckPermission("system:productRecord:export")
    @Log(title = "产品台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProductRecordBo bo, HttpServletResponse response) {
        List<IcesProductRecordVo> list = iIcesProductRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "产品台账", IcesProductRecordVo.class, response);
    }

    /**
     * 获取产品台账详细信息
     *
     * @param prrId 主键
     */
    @SaCheckPermission("system:productRecord:query")
    @GetMapping("/{prrId}")
    public R<IcesProductRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long prrId) {
        return R.ok(iIcesProductRecordService.queryById(prrId));
    }

    /**
     * 新增产品台账
     */
    @SaCheckPermission("system:productRecord:add")
    @Log(title = "产品台账", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProductRecordBo bo) {
        return toAjax(iIcesProductRecordService.insertByBo(bo));
    }

    /**
     * 修改产品台账
     */
    @SaCheckPermission("system:productRecord:edit")
    @Log(title = "产品台账", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProductRecordBo bo) {
        return toAjax(iIcesProductRecordService.updateByBo(bo));
    }

    /**
     * 删除产品台账
     *
     * @param prrIds 主键串
     */
    @SaCheckPermission("system:productRecord:remove")
    @Log(title = "产品台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{prrIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] prrIds) {
        return toAjax(iIcesProductRecordService.deleteWithValidByIds(Arrays.asList(prrIds), true));
    }
}
