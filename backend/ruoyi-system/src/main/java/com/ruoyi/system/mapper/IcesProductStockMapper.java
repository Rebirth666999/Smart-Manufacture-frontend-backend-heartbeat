package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.IcesProductStock;
import com.ruoyi.system.domain.vo.IcesProductStockVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库产品库存Mapper接口
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Mapper
public interface IcesProductStockMapper extends BaseMapperPlus<IcesProductStockMapper, IcesProductStock, IcesProductStockVo> {

}
