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
import com.ruoyi.system.domain.vo.IcesStoreVo;
import com.ruoyi.system.domain.bo.IcesStoreBo;
import com.ruoyi.system.service.IIcesStoreService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/store")
public class IcesStoreController extends BaseController {

    private final IIcesStoreService iIcesStoreService;

    /**
     * 查询仓库列表
     */
    @SaCheckPermission("system:store:list")
    @GetMapping("/list")
    public TableDataInfo<IcesStoreVo> list(IcesStoreBo bo, PageQuery pageQuery) {
        return iIcesStoreService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出仓库列表
     */
    @SaCheckPermission("system:store:export")
    @Log(title = "仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesStoreBo bo, HttpServletResponse response) {
        List<IcesStoreVo> list = iIcesStoreService.queryList(bo);
        ExcelUtil.exportExcel(list, "仓库", IcesStoreVo.class, response);
    }

    /**
     * 获取仓库详细信息
     *
     * @param stId 主键
     */
    @SaCheckPermission("system:store:query")
    @GetMapping("/{stId}")
    public R<IcesStoreVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long stId) {
        return R.ok(iIcesStoreService.queryById(stId));
    }

    /**
     * 新增仓库
     */
    @SaCheckPermission("system:store:add")
    @Log(title = "仓库", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesStoreBo bo) {
        return toAjax(iIcesStoreService.insertByBo(bo));
    }

    /**
     * 修改仓库
     */
    @SaCheckPermission("system:store:edit")
    @Log(title = "仓库", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesStoreBo bo) {
        return toAjax(iIcesStoreService.updateByBo(bo));
    }

    /**
     * 删除仓库
     *
     * @param stIds 主键串
     */
    @SaCheckPermission("system:store:remove")
    @Log(title = "仓库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{stIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] stIds) {
        return toAjax(iIcesStoreService.deleteWithValidByIds(Arrays.asList(stIds), true));
    }
}
