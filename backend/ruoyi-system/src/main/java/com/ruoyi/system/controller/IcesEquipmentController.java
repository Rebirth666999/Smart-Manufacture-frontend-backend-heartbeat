package com.ruoyi.system.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import com.ruoyi.system.domain.bo.IcesEquipmentRecordBo;
import com.ruoyi.system.service.IIcesEquipmentRecordService;
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
import com.ruoyi.system.domain.vo.IcesEquipmentVo;
import com.ruoyi.system.domain.bo.IcesEquipmentBo;
import com.ruoyi.system.service.IIcesEquipmentService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import java.util.Date;
/**
 * 设备控制器，用于管理设备相关的操作
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/equipment")
public class IcesEquipmentController extends BaseController {

    private final IIcesEquipmentService iIcesEquipmentService;
    private final IIcesEquipmentRecordService iIcesEquipmentRecordService;
    /**
     * 查询设备列表
     *
     * @param bo 设备查询参数对象
     * @param pageQuery 分页查询参数
     * @return 设备列表信息
     */
    @SaCheckPermission("system:equipment:list")
    @GetMapping("/list")
    public TableDataInfo<IcesEquipmentVo> list(IcesEquipmentBo bo, PageQuery pageQuery) {
        return iIcesEquipmentService.queryPageList(bo, pageQuery);
    }

    /**
     * 审核端查询设备模型列表
     */
    @GetMapping("/reviewList")
    public TableDataInfo<IcesEquipmentVo> reviewList(IcesEquipmentBo bo, PageQuery pageQuery) {
        return iIcesEquipmentService.queryReviewList(bo, pageQuery);
    }

    /**
     * 导出设备列表到Excel
     *
     * @param bo 设备查询参数对象
     * @param response HTTP响应对象，用于输出Excel文件
     */
    @SaCheckPermission("system:equipment:export")
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(IcesEquipmentBo bo, HttpServletResponse response) {
        List<IcesEquipmentVo> list = iIcesEquipmentService.queryList(bo);
        ExcelUtil.exportExcel(list, "设备", IcesEquipmentVo.class, response);
    }

    /**
     * 获取设备详细信息
     *
     * @param eqId 设备主键ID
     * @return 设备详细信息
     */
    @SaCheckPermission("system:equipment:query")
    @GetMapping("/{eqId}")
    public R<IcesEquipmentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long eqId) {
        return R.ok(iIcesEquipmentService.queryById(eqId));
    }

    /**
     * 新增设备信息
     *
     * @param bo 设备新增参数对象
     * @return 新增结果
     */
    @SaCheckPermission("system:equipment:add")
    @Log(title = "设备", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody IcesEquipmentBo bo) {
        boolean result = iIcesEquipmentService.insertByBo(bo);

        // 如果设备添加成功，记录设备事件日志
        if (result && bo.getEqCode() != null) {
            String description = "新增设备：" + (StringUtils.isNotEmpty(bo.getEqName()) ? bo.getEqName() : bo.getEqCode());
            IcesEquipmentRecordBo recordBo = new IcesEquipmentRecordBo();
            recordBo.setEqCode(bo.getEqCode());
            recordBo.setErBegin(new Date());
            recordBo.setErCode(bo.getEqCode());
            recordBo.setErType("1");
            recordBo.setErStat("2");
            recordBo.setErDelete(0L);
            recordBo.setErLevel(1);
            recordBo.setErDesc(description);
            iIcesEquipmentRecordService.insertByBo(recordBo);
        }
        return toAjax(result);
    }


    /**
     * 修改设备信息
     *
     * @param bo 设备修改参数对象
     * @return 修改结果
     */
    @SaCheckPermission("system:equipment:edit")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody IcesEquipmentBo bo) {
        boolean result = iIcesEquipmentService.updateByBo(bo);

        // 如果设备修改成功，记录设备事件日志
        if (result && bo.getEqCode() != null) {
            String description = "修改设备：" + (StringUtils.isNotEmpty(bo.getEqName()) ? bo.getEqName() : bo.getEqCode());
            IcesEquipmentRecordBo recordBo = new IcesEquipmentRecordBo();
            recordBo.setEqCode(bo.getEqCode());
            recordBo.setErBegin(new Date());
            recordBo.setErCode(bo.getEqCode());
            recordBo.setErType("c"); // 'u' 表示更新操作
            recordBo.setErStat("2");
            recordBo.setErDelete(0L);
            recordBo.setErLevel(1);
            recordBo.setErDesc(description);
            iIcesEquipmentRecordService.insertByBo(recordBo);
        }
        return toAjax(result);
    }

    /**
     * 删除设备信息
     *
     * @param eqIds 设备主键ID数组
     * @return 删除结果
     */
    @SaCheckPermission("system:equipment:remove")
    @Log(title = "设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eqIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] eqIds) {
        return toAjax(iIcesEquipmentService.deleteWithValidByIds(Arrays.asList(eqIds), true));
    }
}
