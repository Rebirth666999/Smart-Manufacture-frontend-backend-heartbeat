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
import com.ruoyi.system.domain.vo.IcesProductVo;
import com.ruoyi.system.domain.bo.IcesProductBo;
import com.ruoyi.system.service.IIcesProductService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品
 *
 * @author ruoyi
 * @date 2025-02-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/product")
public class IcesProductController extends BaseController {

    private final IIcesProductService iIcesProductService;

    /**
     * 查询产品列表
     */
    @SaCheckPermission("system:product:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProductVo> list(IcesProductBo bo, PageQuery pageQuery) {
        return iIcesProductService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出产品列表
     */
    @SaCheckPermission("system:product:export")
    @Log(title = "产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProductBo bo, HttpServletResponse response) {
        List<IcesProductVo> list = iIcesProductService.queryList(bo);
        ExcelUtil.exportExcel(list, "产品", IcesProductVo.class, response);
    }

    /**
     * 获取产品详细信息
     *
     * @param prId 主键
     */
    @SaCheckPermission("system:product:query")
    @GetMapping("/{prId}")
    public R<IcesProductVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long prId) {
        return R.ok(iIcesProductService.queryById(prId));
    }

    /**
     * 新增产品
     */
    @SaCheckPermission("system:product:add")
    @Log(title = "产品", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProductBo bo) {
        return toAjax(iIcesProductService.insertByBo(bo));
    }

    /**
     * 修改产品
     */
    @SaCheckPermission("system:product:edit")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProductBo bo) {
        return toAjax(iIcesProductService.updateByBo(bo));
    }

    /**
     * 删除产品
     *
     * @param prIds 主键串
     */
    @SaCheckPermission("system:product:remove")
    @Log(title = "产品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{prIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] prIds) {
        return toAjax(iIcesProductService.deleteWithValidByIds(Arrays.asList(prIds), true));
    }
}
