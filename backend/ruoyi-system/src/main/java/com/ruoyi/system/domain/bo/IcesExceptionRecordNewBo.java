package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常记录（新）业务对象 ices_exception_record_new
 *
 * @author ruoyi
 * @date 2025-07-30
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionRecordNewBo extends BaseEntity {

    /**
     * 异常记录ID
     */
    private Long exrId;

    /**
     * 异常记录编码
     */

    private String exrCode;

    /**
     * 异常源
     */
    @NotBlank(message = "异常源不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exsCode;

    /**
     * 异常
     */
    @NotBlank(message = "异常不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exCode;

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrDesc;

    /**
     * 参数(JSON形式)
     */
    @NotBlank(message = "参数(JSON形式)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrParam;

    /**
     * 状态(数据字典)
     */
    @NotBlank(message = "状态(数据字典)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrStat;

    /**
     * 等级(数据字典)
     */
    @NotBlank(message = "等级(数据字典)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrLevel;

    /**
     * 异常上报人
     */
    @NotBlank(message = "异常上报人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrUserReport;

    /**
     * 当前处理人
     */
    private String exrUserHandle;

    /**
     * 异常解除人
     */
    private String exrUserFinish;

    /**
     * 异常责任人
     */
    @NotBlank(message = "异常责任人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrUserResp;

    /**
     * 上报时间
     */
    private String exrCdate;

    /**
     * 影响因子(百分比描述)
     */
    @NotNull(message = "影响因子(百分比描述)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long exrImpactFactor;

    /**
     * 影响等级(数据字典)
     */
    @NotBlank(message = "影响等级(数据字典)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrImpactLevel;

    /**
     * 异常执行流程
     */
    private String exrProcess;

    /**
     * 持续时间(秒)
     */
    @NotNull(message = "持续时间(秒)不能为空", groups = { EditGroup.class })
    private Long exrDuration;

    /**
     * 异常图片路径
     */
    private String exrImg;

    /**
     * 处理流程
     */
    private String exrPro;

    /**
     * 异常处理完成图片
     */
    private String exrFinImg;

    /**
     * Ai确认是否是异常
     */
    private String exrAiVer;

    /**
     * Ai确认异常分类
     */
    private String exrAiCls;

    /**
     * 人工确认异常分类
     */
    private String exrHumCls;

    /**
     * 涉及到的部门
     */
    private String exrDep;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long exrDelete;


}
