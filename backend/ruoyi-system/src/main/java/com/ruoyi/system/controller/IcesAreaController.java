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
import com.ruoyi.system.domain.vo.IcesAreaVo;
import com.ruoyi.system.domain.bo.IcesAreaBo;
import com.ruoyi.system.service.IIcesAreaService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车间
 *
 * @author ruoyi
 * @date 2025-02-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/area")
public class IcesAreaController extends BaseController {

    private final IIcesAreaService iIcesAreaService;

    /**
     * 查询车间列表
     */
    @SaCheckPermission("system:area:list")
    @GetMapping("/list")
    public TableDataInfo<IcesAreaVo> list(IcesAreaBo bo, PageQuery pageQuery) {
        return iIcesAreaService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出车间列表
     */
    @SaCheckPermission("system:area:export")
    @Log(title = "车间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesAreaBo bo, HttpServletResponse response) {
        List<IcesAreaVo> list = iIcesAreaService.queryList(bo);
        ExcelUtil.exportExcel(list, "车间", IcesAreaVo.class, response);
    }

    /**
     * 获取车间详细信息
     *
     * @param arId 主键
     */
    @SaCheckPermission("system:area:query")
    @GetMapping("/{arId}")
    public R<IcesAreaVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long arId) {
        return R.ok(iIcesAreaService.queryById(arId));
    }

    /**
     * 新增车间
     */
    @SaCheckPermission("system:area:add")
    @Log(title = "车间", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesAreaBo bo) {
        return toAjax(iIcesAreaService.insertByBo(bo));
    }

    /**
     * 修改车间
     */
    @SaCheckPermission("system:area:edit")
    @Log(title = "车间", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesAreaBo bo) {
        return toAjax(iIcesAreaService.updateByBo(bo));
    }

    /**
     * 删除车间
     *
     * @param arIds 主键串
     */
    @SaCheckPermission("system:area:remove")
    @Log(title = "车间", businessType = BusinessType.DELETE)
    @DeleteMapping("/{arIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] arIds) {
        return toAjax(iIcesAreaService.deleteWithValidByIds(Arrays.asList(arIds), true));
    }
}
