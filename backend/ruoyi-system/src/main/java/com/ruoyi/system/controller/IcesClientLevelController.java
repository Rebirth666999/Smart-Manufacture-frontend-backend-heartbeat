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
import com.ruoyi.system.domain.vo.IcesClientLevelVo;
import com.ruoyi.system.domain.bo.IcesClientLevelBo;
import com.ruoyi.system.service.IIcesClientLevelService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户等级
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/clientLevel")
public class IcesClientLevelController extends BaseController {

    private final IIcesClientLevelService iIcesClientLevelService;

    /**
     * 查询客户等级列表
     */
    @SaCheckPermission("system:clientLevel:list")
    @GetMapping("/list")
    public TableDataInfo<IcesClientLevelVo> list(IcesClientLevelBo bo, PageQuery pageQuery) {
        return iIcesClientLevelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出客户等级列表
     */
    @SaCheckPermission("system:clientLevel:export")
    @Log(title = "客户等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesClientLevelBo bo, HttpServletResponse response) {
        List<IcesClientLevelVo> list = iIcesClientLevelService.queryList(bo);
        ExcelUtil.exportExcel(list, "客户等级", IcesClientLevelVo.class, response);
    }

    /**
     * 获取客户等级详细信息
     *
     * @param cllId 主键
     */
    @SaCheckPermission("system:clientLevel:query")
    @GetMapping("/{cllId}")
    public R<IcesClientLevelVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long cllId) {
        return R.ok(iIcesClientLevelService.queryById(cllId));
    }

    /**
     * 新增客户等级
     */
    @SaCheckPermission("system:clientLevel:add")
    @Log(title = "客户等级", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesClientLevelBo bo) {
        return toAjax(iIcesClientLevelService.insertByBo(bo));
    }

    /**
     * 修改客户等级
     */
    @SaCheckPermission("system:clientLevel:edit")
    @Log(title = "客户等级", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesClientLevelBo bo) {
        return toAjax(iIcesClientLevelService.updateByBo(bo));
    }

    /**
     * 删除客户等级
     *
     * @param cllIds 主键串
     */
    @SaCheckPermission("system:clientLevel:remove")
    @Log(title = "客户等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{cllIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] cllIds) {
        return toAjax(iIcesClientLevelService.deleteWithValidByIds(Arrays.asList(cllIds), true));
    }
}
