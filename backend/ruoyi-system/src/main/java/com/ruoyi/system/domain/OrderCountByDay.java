package com.ruoyi.system.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 按天统计结果
@Getter
@Setter
@Data
public class OrderCountByDay {
    private Integer day;    // 日期（1-31）
    private Integer count;  // 订单数量
}
