package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.IcesOrderBo;
import com.ruoyi.system.domain.vo.IcesOrderVo;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesOrderDemandBo;
import com.ruoyi.system.domain.vo.IcesOrderDemandVo;
import com.ruoyi.system.domain.IcesOrderDemand;
import com.ruoyi.system.mapper.IcesOrderDemandMapper;
import com.ruoyi.system.service.IIcesOrderDemandService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单所需产品关联Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-10
 */
@RequiredArgsConstructor
@Service
public class IcesOrderDemandServiceImpl implements IIcesOrderDemandService {

    private final IcesOrderDemandMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesOrderService orderService;

    /**
     * 查询订单所需产品关联
     */
    @Override
    public IcesOrderDemandVo queryById(Long odId){
        return baseMapper.selectVoById(odId);
    }

    /**
     * 查询订单所需产品关联列表
     */
    @Override
    public TableDataInfo<IcesOrderDemandVo> queryPageList(IcesOrderDemandBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesOrderDemand> lqw = buildQueryWrapper(bo);
        Page<IcesOrderDemandVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单所需产品关联列表
     */
    @Override
    public List<IcesOrderDemandVo> queryList(IcesOrderDemandBo bo) {
        LambdaQueryWrapper<IcesOrderDemand> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesOrderDemand> buildQueryWrapper(IcesOrderDemandBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesOrderDemand> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOdCode()), IcesOrderDemand::getOdCode, bo.getOdCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrCode()), IcesOrderDemand::getPrCode, bo.getPrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getOrCode()), IcesOrderDemand::getOrCode, bo.getOrCode());
        lqw.eq(bo.getOdDemand() != null, IcesOrderDemand::getOdDemand, bo.getOdDemand());
        lqw.eq(bo.getOdDelete() != null, IcesOrderDemand::getOdDelete, bo.getOdDelete());
        return lqw;
    }

    /**
     * 新增订单所需产品关联
     */
    @Override
    public Boolean insertByBo(IcesOrderDemandBo bo) {
        bo.setOdCode(codeService.insertByType("OrderDemand"));
        IcesOrderDemand add = BeanUtil.toBean(bo, IcesOrderDemand.class);
        validEntityBeforeSave(add);
        updateOrder(bo);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOdId(add.getOdId());
        }
        return flag;
    }

    /**
     * 修改订单所需产品关联
     */
    @Override
    public Boolean updateByBo(IcesOrderDemandBo bo) {
        IcesOrderDemand update = BeanUtil.toBean(bo, IcesOrderDemand.class);
        validEntityBeforeSave(update);
        updateOrder(bo);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 新增/修改订单产品算作对订单信息的修改
     * 需要更新订单信息的修改人、修改时间字段
     */
    private void updateOrder(IcesOrderDemandBo bo) {
        // 搜索条件
        String orCode = bo.getOrCode();
        IcesOrderBo orderBo = new IcesOrderBo();
        orderBo.setOrCode(orCode);
        // 查找列表
        List<IcesOrderVo> vos = orderService.queryList(orderBo);
        assert vos != null && vos.size() == 1;
        // 调用更新（设置字段在目标方法进行）
        orderBo.setOrCode(null);
        orderBo.setOrId(vos.get(0).getOrId());
        orderBo.setOrStat(vos.get(0).getOrStat());
        orderService.updateByBo(orderBo);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesOrderDemand entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除订单所需产品关联
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 给更新的订单配置产品
     * @param json 前端传来数据
     * @return 是否成功
     * @author YangZY
     * @date 20250508
     */
    @Override
    public Boolean updateWithOrder(String json) throws JsonProcessingException {
        // 接收参数
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.readValue(json, Map.class);
        // order是对象
        IcesOrderVo order = BeanUtil.toBean(data.get("order"), IcesOrderVo.class);
        // demand是对象数组
        JSONArray demandArray = JSONUtil.parseArray(data.get("demand"));
        List<IcesOrderDemandBo> demand = demandArray.toList(IcesOrderDemandBo.class);
        // 为新的订单插入产品需求
        for (IcesOrderDemandBo demandBo : demand) {
            demandBo.setOdId(null);
            demandBo.setOdCode(null);
            demandBo.setOrCode(order.getOrCode());
            insertByBo(demandBo);
        }
        return true;
    }
}
