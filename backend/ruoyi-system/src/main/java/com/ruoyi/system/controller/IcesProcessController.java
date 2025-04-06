package com.ruoyi.system.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.system.domain.bo.IcesEquipmentModelBo;
import com.ruoyi.system.domain.vo.IcesEquipmentModelVo;
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
import com.ruoyi.system.domain.vo.IcesProcessVo;
import com.ruoyi.system.domain.bo.IcesProcessBo;
import com.ruoyi.system.service.IIcesProcessService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺流程
 *
 * @author ruoyi
 * @date 2025-03-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/process")
public class IcesProcessController extends BaseController {

    private final IIcesProcessService iIcesProcessService;

    /**
     * 查询工艺流程列表
     */
    @SaCheckPermission("system:process:list")
    @GetMapping("/list")
    public TableDataInfo<IcesProcessVo> list(IcesProcessBo bo, PageQuery pageQuery) {
        return iIcesProcessService.queryPageList(bo, pageQuery);
    }

    /**
     * 审核端查询设备模型列表
     */
    @GetMapping("/reviewList")
    public TableDataInfo<IcesProcessVo> reviewList(IcesProcessBo bo, PageQuery pageQuery) {
        return iIcesProcessService.queryReviewList(bo, pageQuery);
    }

    /**
     * 导出工艺流程列表
     */
    @SaCheckPermission("system:process:export")
    @Log(title = "工艺流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesProcessBo bo, HttpServletResponse response) {
        List<IcesProcessVo> list = iIcesProcessService.queryList(bo);
        ExcelUtil.exportExcel(list, "工艺流程", IcesProcessVo.class, response);
    }

    /**
     * 获取工艺流程详细信息
     *
     * @param procId 主键
     */
    @SaCheckPermission("system:process:query")
    @GetMapping("/{procId}")
    public R<IcesProcessVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long procId) {
        return R.ok(iIcesProcessService.queryById(procId));
    }

    /**
     * 新增工艺流程
     */
    @SaCheckPermission("system:process:add")
    @Log(title = "工艺流程", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesProcessBo bo) {
        return toAjax(iIcesProcessService.insertByBo(bo));
    }

    /**
     * 修改工艺流程
     */
    @SaCheckPermission("system:process:edit")
    @Log(title = "工艺流程", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesProcessBo bo) {
        return toAjax(iIcesProcessService.updateByBo(bo));
    }

    /**
     * 删除工艺流程
     *
     * @param procIds 主键串
     */
    @SaCheckPermission("system:process:remove")
    @Log(title = "工艺流程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{procIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] procIds) {
        return toAjax(iIcesProcessService.deleteWithValidByIds(Arrays.asList(procIds), true));
    }

    /**
     * 获取设备操作流程模型XML
     *
     * @param modelId 模型主键
     */
    @GetMapping(value = "/model/bpmnXml/{modelId}")
    public R<String> getBpmnXml(@NotNull(message = "主键不能为空") @PathVariable("modelId") String modelId) {
        return R.ok("操作成功", iIcesProcessService.queryBpmnXmlById(modelId));
    }

    /**
     * 根据XML保存模型
     *
     * @param modelXML 模型XML文本
     */
    @PostMapping("/model/save")
    public R<String> save(@RequestBody String modelXML) {
        try {
            iIcesProcessService.saveProcess(modelXML);
            return R.ok();
        } catch (Exception e) {
            return R.fail("操作失败，请检查是否已为所有步骤分配模型操作");
        }
    }

}

