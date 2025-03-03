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
import com.ruoyi.system.domain.vo.IcesAreaControlVo;
import com.ruoyi.system.domain.bo.IcesAreaControlBo;
import com.ruoyi.system.service.IIcesAreaControlService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主控节点
 *
 * @author ruoyi
 * @date 2025-02-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/areaControl")
public class IcesAreaControlController extends BaseController {

    private final IIcesAreaControlService iIcesAreaControlService;

    /**
     * 查询主控节点列表
     */
    @SaCheckPermission("system:areaControl:list")
    @GetMapping("/list")
    public TableDataInfo<IcesAreaControlVo> list(IcesAreaControlBo bo, PageQuery pageQuery) {
        return iIcesAreaControlService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出主控节点列表
     */
    @SaCheckPermission("system:areaControl:export")
    @Log(title = "主控节点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesAreaControlBo bo, HttpServletResponse response) {
        List<IcesAreaControlVo> list = iIcesAreaControlService.queryList(bo);
        ExcelUtil.exportExcel(list, "主控节点", IcesAreaControlVo.class, response);
    }

    /**
     * 获取主控节点详细信息
     *
     * @param acId 主键
     */
    @SaCheckPermission("system:areaControl:query")
    @GetMapping("/{acId}")
    public R<IcesAreaControlVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long acId) {
        return R.ok(iIcesAreaControlService.queryById(acId));
    }

    /**
     * 新增主控节点
     */
    @SaCheckPermission("system:areaControl:add")
    @Log(title = "主控节点", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesAreaControlBo bo) {
        return toAjax(iIcesAreaControlService.insertByBo(bo));
    }

    /**
     * 修改主控节点
     */
    @SaCheckPermission("system:areaControl:edit")
    @Log(title = "主控节点", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesAreaControlBo bo) {
        return toAjax(iIcesAreaControlService.updateByBo(bo));
    }

    /**
     * 删除主控节点
     *
     * @param acIds 主键串
     */
    @SaCheckPermission("system:areaControl:remove")
    @Log(title = "主控节点", businessType = BusinessType.DELETE)
    @DeleteMapping("/{acIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] acIds) {
        return toAjax(iIcesAreaControlService.deleteWithValidByIds(Arrays.asList(acIds), true));
    }
}
