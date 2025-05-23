package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.IcesOrder;
import com.ruoyi.system.domain.OrderCountByDay;
import com.ruoyi.system.domain.OrderCountByMonth;
import com.ruoyi.system.domain.vo.IcesOrderVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.service.impl.IcesOrderServiceImpl;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单Mapper接口
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Mapper
public interface IcesOrderMapper extends BaseMapperPlus<IcesOrderMapper, IcesOrder, IcesOrderVo> {
    // 按年统计各月订单数量
    List<OrderCountByMonth> countByYear(LocalDateTime startDate, LocalDateTime endDate);

    // 按月统计每天订单数量
    List<OrderCountByDay> countByMonth(LocalDateTime startDate, LocalDateTime endDate);

    // 按季度统计各月订单数量
    List<OrderCountByMonth> countByQuarter(LocalDateTime startDate, LocalDateTime endDate);
}
