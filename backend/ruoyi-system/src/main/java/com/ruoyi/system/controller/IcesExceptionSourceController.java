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
import com.ruoyi.system.domain.vo.IcesExceptionSourceVo;
import com.ruoyi.system.domain.bo.IcesExceptionSourceBo;
import com.ruoyi.system.service.IIcesExceptionSourceService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常源
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionSource")
public class IcesExceptionSourceController extends BaseController {

    private final IIcesExceptionSourceService iIcesExceptionSourceService;

    /**
     * 查询异常源列表
     */
    @SaCheckPermission("system:exceptionSource:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionSourceVo> list(IcesExceptionSourceBo bo, PageQuery pageQuery) {
        return iIcesExceptionSourceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出异常源列表
     */
    @SaCheckPermission("system:exceptionSource:export")
    @Log(title = "异常源", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionSourceBo bo, HttpServletResponse response) {
        List<IcesExceptionSourceVo> list = iIcesExceptionSourceService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常源", IcesExceptionSourceVo.class, response);
    }

    /**
     * 获取异常源详细信息
     *
     * @param exsId 主键
     */
    @SaCheckPermission("system:exceptionSource:query")
    @GetMapping("/{exsId}")
    public R<IcesExceptionSourceVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exsId) {
        return R.ok(iIcesExceptionSourceService.queryById(exsId));
    }

    /**
     * 新增异常源
     */
    @SaCheckPermission("system:exceptionSource:add")
    @Log(title = "异常源", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesExceptionSourceBo bo) {
        return toAjax(iIcesExceptionSourceService.insertByBo(bo));
    }

    /**
     * 修改异常源
     */
    @SaCheckPermission("system:exceptionSource:edit")
    @Log(title = "异常源", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionSourceBo bo) {
        return toAjax(iIcesExceptionSourceService.updateByBo(bo));
    }

    /**
     * 删除异常源
     *
     * @param exsIds 主键串
     */
    @SaCheckPermission("system:exceptionSource:remove")
    @Log(title = "异常源", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exsIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exsIds) {
        return toAjax(iIcesExceptionSourceService.deleteWithValidByIds(Arrays.asList(exsIds), true));
    }
}
