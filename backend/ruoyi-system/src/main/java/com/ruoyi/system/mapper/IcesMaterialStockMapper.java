package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.IcesMaterialStock;
import com.ruoyi.system.domain.vo.IcesMaterialStockVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 关联-车间原料库存Mapper接口
 *
 * @author ruoyi
 * @date 2025-04-01
 */
@Mapper
public interface IcesMaterialStockMapper extends BaseMapperPlus<IcesMaterialStockMapper, IcesMaterialStock, IcesMaterialStockVo> {

}
