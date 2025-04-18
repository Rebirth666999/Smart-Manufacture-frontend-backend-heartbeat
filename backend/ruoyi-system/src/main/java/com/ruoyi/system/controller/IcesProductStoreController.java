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
import com.ruoyi.system.domain.vo.IcesProductStoreVo;
import com.ruoyi.system.domain.bo.IcesProductStoreBo;
import com.ruoyi.system.service.IIcesProductStoreService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品仓库
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/productStore")
public class IcesProductStoreController extends BaseController {

    private final IIcesProductStoreService iIcesProductStoreService;

    /**
     * 查询产品仓库列表
     */
    @SaCheckPermission("system:productStore:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProductStoreVo> list(IcesProductStoreBo bo, PageQuery pageQuery) {
        return iIcesProductStoreService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出产品仓库列表
     */
    @SaCheckPermission("system:productStore:export")
    @Log(title = "产品仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProductStoreBo bo, HttpServletResponse response) {
        List<IcesProductStoreVo> list = iIcesProductStoreService.queryList(bo);
        ExcelUtil.exportExcel(list, "产品仓库", IcesProductStoreVo.class, response);
    }

    /**
     * 获取产品仓库详细信息
     *
     * @param prsId 主键
     */
    @SaCheckPermission("system:productStore:query")
    @GetMapping("/{prsId}")
    public R<IcesProductStoreVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long prsId) {
        return R.ok(iIcesProductStoreService.queryById(prsId));
    }

    /**
     * 新增产品仓库
     */
    @SaCheckPermission("system:productStore:add")
    @Log(title = "产品仓库", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProductStoreBo bo) {
        return toAjax(iIcesProductStoreService.insertByBo(bo));
    }

    /**
     * 修改产品仓库
     */
    @SaCheckPermission("system:productStore:edit")
    @Log(title = "产品仓库", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProductStoreBo bo) {
        return toAjax(iIcesProductStoreService.updateByBo(bo));
    }

    /**
     * 删除产品仓库
     *
     * @param prsIds 主键串
     */
    @SaCheckPermission("system:productStore:remove")
    @Log(title = "产品仓库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{prsIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] prsIds) {
        return toAjax(iIcesProductStoreService.deleteWithValidByIds(Arrays.asList(prsIds), true));
    }
}
