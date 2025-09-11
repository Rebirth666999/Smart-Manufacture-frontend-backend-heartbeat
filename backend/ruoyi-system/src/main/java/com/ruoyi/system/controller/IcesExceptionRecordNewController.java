package com.ruoyi.system.controller;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import com.ruoyi.system.domain.bo.IcesExceptionRecordAiBo;
import com.ruoyi.system.domain.dto.ContentAnalysisDTO;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import liquibase.pro.packaged.G;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.IcesExceptionRecordNewVo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordNewBo;
import com.ruoyi.system.service.IIcesExceptionRecordNewService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常记录（新）
 *
 * @author ruoyi
 * @date 2025-07-30
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exceptionRecordNew")
public class IcesExceptionRecordNewController extends BaseController {

    private final IIcesExceptionRecordNewService iIcesExceptionRecordNewService;

    /**
     * 查询异常记录（新）列表
     */
    @SaCheckPermission("system:exceptionRecordNew:list")
    @GetMapping("/list")
    public TableDataInfo<IcesExceptionRecordNewVo> list(IcesExceptionRecordNewBo bo, PageQuery pageQuery) {
        return iIcesExceptionRecordNewService.queryPageList(bo, pageQuery);
    }

    //启动流程实例
    @SaCheckPermission("system:exceptionRecordNew:export")
    @PostMapping("/startLifecycle")
    public R<String> startLifecycle( @RequestBody IcesExceptionRecordNewBo bo) {
        String procInsId = iIcesExceptionRecordNewService.startLifecycleNew(bo);
        return R.ok(procInsId);
    }
    /**
     * 导出异常记录（新）列表
     */
    @SaCheckPermission("system:exceptionRecordNew:export")
    @Log(title = "异常记录（新）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesExceptionRecordNewBo bo, HttpServletResponse response) {
        List<IcesExceptionRecordNewVo> list = iIcesExceptionRecordNewService.queryList(bo);
        ExcelUtil.exportExcel(list, "异常记录（新）", IcesExceptionRecordNewVo.class, response);
    }

    /**
     * 获取异常记录（新）详细信息
     *
     * @param exrId 主键
     */
    @SaCheckPermission("system:exceptionRecordNew:query")
    @GetMapping("/{exrId}")
    public R<IcesExceptionRecordNewVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long exrId) {
        return R.ok(iIcesExceptionRecordNewService.queryById(exrId));
    }

    /**
     * 新增异常记录（新）
     */
    @SaCheckPermission("system:exceptionRecordNew:add")
    @Log(title = "异常记录（新）", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<IcesExceptionRecordNewVo> add(@Validated(AddGroup.class) @RequestBody IcesExceptionRecordNewBo bo) {
        return R.ok(iIcesExceptionRecordNewService.insertByBo(bo));
    }

    /**
     * 新增AI异常记录!!!!!!!!!!
     */
    //@SaCheckPermission("system:exceptionRecord:add")
    @Log(title = "异常记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/ai")
    public R<IcesExceptionRecordNewVo> addAi(@Validated(AddGroup.class) @RequestBody IcesExceptionRecordAiBo bo) {
        return R.ok(iIcesExceptionRecordNewService.insertByBo(bo));
    }

    /**
     * 修改异常记录（新）
     */
    @SaCheckPermission("system:exceptionRecordNew:edit")
    @Log(title = "异常记录（新）", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesExceptionRecordNewBo bo) {
        return toAjax(iIcesExceptionRecordNewService.updateByBo(bo));
    }

    /**
     * 删除异常记录（新）
     *
     * @param exrIds 主键串
     */
    @SaCheckPermission("system:exceptionRecordNew:remove")
    @Log(title = "异常记录（新）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{exrIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] exrIds) {
        return toAjax(iIcesExceptionRecordNewService.deleteWithValidByIds(Arrays.asList(exrIds), true));
    }

    /**
     * 提取分析内容并保存到数据库
     */
//    @SaCheckPermission("system:exceptionRecordNew:edit")
    @Log(title = "异常记录（新）", businessType = BusinessType.UPDATE)
    @PostMapping("/extractAndSave")
    public R<Void> extractAndSaveContent(
        @Valid @RequestBody ContentAnalysisDTO analysisDTO) {
        Long exrId = analysisDTO.getExrId();
        // 从数据中提取type为"answer"的content
//        Optional<ContentAnalysisDTO.ContentItemDTO> targetItem = analysisDTO.getData().stream()
//            .filter(item -> "answer".equals(item.getType()))
//            .findFirst();
        Long exrStat = analysisDTO.getExrStat();
        if (analysisDTO.getData() == null || analysisDTO.getData().isEmpty()) {
            System.out.println("data 为空或未包含任何内容");
            return R.fail("未找到有效的分析内容");
        }

        Optional<ContentAnalysisDTO.ContentItemDTO> targetItem = analysisDTO.getData().stream()
            .filter(item -> "answer".equals(item.getType()))
            .findFirst();

        if (targetItem.isPresent()) {
            String analysisContent = targetItem.get().getContent();
            // 调用服务层方法保存到数据库
            System.out.println("要保存的内容：" + analysisContent +"正在调用service");
            boolean success = iIcesExceptionRecordNewService.saveAnalysisContent(exrId, analysisContent, exrStat);
            if (success) {
                System.out.println("保存成功");
                return R.ok(analysisContent);
            } else {
                System.out.println("保存失败，未找到对应的异常记录");
                return R.fail("保存失败，未找到对应的异常记录");
            }
        } else {
            System.out.println("未找到有效的分析内容");
            return R.fail("未找到有效的分析内容");
        }
    }

}
