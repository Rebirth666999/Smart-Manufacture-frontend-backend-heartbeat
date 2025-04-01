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
import com.ruoyi.system.domain.vo.IcesRefundRecordVo;
import com.ruoyi.system.domain.bo.IcesRefundRecordBo;
import com.ruoyi.system.service.IIcesRefundRecordService;
import com.ruoyi.common.core.page.TableDataInfo;


/**
 * 退货记录
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/refundRecord")
public class IcesRefundRecordController extends BaseController {

    private final IIcesRefundRecordService iIcesRefundRecordService;

    /**
     * 查询退货记录列表
     */
    @SaCheckPermission("system:refundRecord:list")
    @GetMapping("/list")
    public TableDataInfo<IcesRefundRecordVo> list(IcesRefundRecordBo bo, PageQuery pageQuery) {
        return iIcesRefundRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出退货记录列表
     */
    @SaCheckPermission("system:refundRecord:export")
    @Log(title = "退货记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesRefundRecordBo bo, HttpServletResponse response) {
        List<IcesRefundRecordVo> list = iIcesRefundRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "退货记录", IcesRefundRecordVo.class, response);
    }

    /**
     * 获取退货记录详细信息
     *
     * @param rrId 主键
     */
    @SaCheckPermission("system:refundRecord:query")
    @GetMapping("/{rrId}")
    public R<IcesRefundRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long rrId) {
        return R.ok(iIcesRefundRecordService.queryById(rrId));
    }

    /**
     * 新增退货记录
     */
    @SaCheckPermission("system:refundRecord:add")
    @Log(title = "退货记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesRefundRecordBo bo) {
        return toAjax(iIcesRefundRecordService.insertByBo(bo));
    }

    /**
     * 修改退货记录
     */
    @SaCheckPermission("system:refundRecord:edit")
    @Log(title = "退货记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesRefundRecordBo bo) {
        return toAjax(iIcesRefundRecordService.updateByBo(bo));
    }

    /**
     * 删除退货记录
     *
     * @param rrIds 主键串
     */
    @SaCheckPermission("system:refundRecord:remove")
    @Log(title = "退货记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{rrIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] rrIds) {
        return toAjax(iIcesRefundRecordService.deleteWithValidByIds(Arrays.asList(rrIds), true));
    }
}
