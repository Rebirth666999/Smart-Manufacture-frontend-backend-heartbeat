package com.ruoyi.system.service;

import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.domain.vo.IcesOrderVo;
import com.ruoyi.system.domain.bo.IcesOrderBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单Service接口
 *
 * @author ruoyi
 * @date 2025-03-17
 */
public interface IIcesOrderService {

    /**
     * 查询订单
     */
    IcesOrderVo queryById(Long orId);

    /**
     * 查询订单列表
     */
    TableDataInfo<IcesOrderVo> queryPageList(IcesOrderBo bo, PageQuery pageQuery);

    /**
     * 审核端查询状态
     */
    TableDataInfo<IcesOrderVo> queryReviewList(IcesOrderBo bo, PageQuery pageQuery);

    /**
     * 查询订单列表
     */
    List<IcesOrderVo> queryList(IcesOrderBo bo);

    /**
     * 新增订单
     */
    IcesOrderVo insertByBo(IcesOrderBo bo);

    /**
     * 修改订单
     */
    IcesOrderVo updateByBo(IcesOrderBo bo);

    /**
     * 校验并批量删除订单信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    void updateStatus(IcesManufacturePlanBo icesManufacturePlanBo);

    /**
     * 按年份统计订单数量
     * @param year 年份（如：2023）
     * @return 各月订单数量（索引0-11对应1-12月）
     */
    List<Integer> countByYear(int year);

    /**
     * 按月份统计订单数量
     * @param year 年份
     * @param month 月份（1-12）
     * @return 当月每天的订单数量
     */
    List<Integer> countByMonth(int year, int month);

    /**
     * 按季度统计订单数量
     * @param year 年份
     * @param quarter 季度（1-4）
     * @return 该季度各月的订单数量（长度为3）
     */
    List<Integer> countByQuarter(int year, int quarter);
}
