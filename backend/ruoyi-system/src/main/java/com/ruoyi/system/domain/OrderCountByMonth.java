package com.ruoyi.system.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderCountByMonth {
    // getters and setters
    private Integer month;  // 月份（1-12）
    private Integer count;  // 订单数量

}
