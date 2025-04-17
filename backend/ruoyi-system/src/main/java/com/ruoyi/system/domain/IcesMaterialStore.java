package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原料仓库对象 ices_material_store
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_material_store")
public class IcesMaterialStore extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 原料仓库ID
     */
    @TableId(value = "ms_id")
    private Long msId;
    /**
     * 原料仓库编码
     */
    private String msCode;
    /**
     * 仓库名称
     */
    private String msName;
    /**
     * 货位数量
     */
    private Long msSpace;
    /**
     * 空闲货位数量
     */
    private Long msFree;
    /**
     * 已删除
     */
    private Long msDelete;
    /**
     * 描述
     */
    private String msDesc;

}
