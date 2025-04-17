package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 原料仓库视图对象 ices_material_store
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@Data
@ExcelIgnoreUnannotated
public class IcesMaterialStoreVo {

    private static final long serialVersionUID = 1L;

    /**
     * 原料仓库ID
     */
    @ExcelProperty(value = "原料仓库ID")
    private Long msId;

    /**
     * 原料仓库编码
     */
    @ExcelProperty(value = "原料仓库编码")
    private String msCode;

    /**
     * 仓库名称
     */
    @ExcelProperty(value = "仓库名称")
    private String msName;

    /**
     * 货位数量
     */
    @ExcelProperty(value = "货位数量")
    private Float msSpace;

    /**
     * 空闲货位数量
     */
    @ExcelProperty(value = "空闲货位数量")
    private Float msFree;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long msDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String msDesc;


}
