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
import com.ruoyi.system.domain.vo.IcesModelOperationVo;
import com.ruoyi.system.domain.bo.IcesModelOperationBo;
import com.ruoyi.system.service.IIcesModelOperationService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备模型操作
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/modelOperation")
public class IcesModelOperationController extends BaseController {

    private final IIcesModelOperationService iIcesModelOperationService;

    /**
     * 查询设备模型操作列表
     */
    @SaCheckPermission("system:modelOperation:list")
    @GetMapping("/list")
    public TableDataInfo<IcesModelOperationVo> list(IcesModelOperationBo bo, PageQuery pageQuery) {
        return iIcesModelOperationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出设备模型操作列表
     */
    @SaCheckPermission("system:modelOperation:export")
    @Log(title = "设备模型操作", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesModelOperationBo bo, HttpServletResponse response) {
        List<IcesModelOperationVo> list = iIcesModelOperationService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备模型操作", IcesModelOperationVo.class, response);
    }

    /**
     * 获取设备模型操作详细信息
     *
     * @param moId 主键
     */
    @SaCheckPermission("system:modelOperation:query")
    @GetMapping("/{moId}")
    public R<IcesModelOperationVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long moId) {
        return R.ok(iIcesModelOperationService.queryById(moId));
    }

    /**
     * 新增设备模型操作
     */
    @SaCheckPermission("system:modelOperation:add")
    @Log(title = "设备模型操作", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesModelOperationBo bo) {
        return toAjax(iIcesModelOperationService.insertByBo(bo));
    }

    /**
     * 修改设备模型操作
     */
    @SaCheckPermission("system:modelOperation:edit")
    @Log(title = "设备模型操作", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesModelOperationBo bo) {
        return toAjax(iIcesModelOperationService.updateByBo(bo));
    }

    /**
     * 删除设备模型操作
     *
     * @param moIds 主键串
     */
    @SaCheckPermission("system:modelOperation:remove")
    @Log(title = "设备模型操作", businessType = BusinessType.DELETE)
    @DeleteMapping("/{moIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] moIds) {
        return toAjax(iIcesModelOperationService.deleteWithValidByIds(Arrays.asList(moIds), true));
    }
}
