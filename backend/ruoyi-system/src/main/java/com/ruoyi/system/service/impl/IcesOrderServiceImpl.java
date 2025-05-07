package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.IcesEquipmentModel;
import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.domain.vo.IcesClientVo;
import com.ruoyi.system.domain.vo.IcesEquipmentModelVo;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesOrderBo;
import com.ruoyi.system.domain.vo.IcesOrderVo;
import com.ruoyi.system.domain.IcesOrder;
import com.ruoyi.system.mapper.IcesOrderMapper;
import com.ruoyi.system.service.IIcesOrderService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@RequiredArgsConstructor
@Service
public class IcesOrderServiceImpl implements IIcesOrderService {

    private final IcesOrderMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询订单
     */
    @Override
    public IcesOrderVo queryById(Long orId){
        return baseMapper.selectVoById(orId);
    }

    /**
     * 查询订单列表
     */
    @Override
    public TableDataInfo<IcesOrderVo> queryPageList(IcesOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesOrder> lqw = buildQueryWrapper(bo);
        Page<IcesOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询审核状态
     */
    @Override
    public TableDataInfo<IcesOrderVo> queryReviewList(IcesOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesOrder> lqw = buildQueryWrapper(bo);
        List<String> stats = new ArrayList<>();
        stats.add("2");  // 待审核
        stats.add("3");  // 审核中
        stats.add("8");  // 待审核（弃用）
        stats.add("9");  // 审核中（弃用）
        lqw.in(IcesOrder::getOrStat, stats);
        Page<IcesOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单列表
     */
    @Override
    public List<IcesOrderVo> queryList(IcesOrderBo bo) {
        LambdaQueryWrapper<IcesOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesOrder> buildQueryWrapper(IcesOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrCode()), IcesOrder::getOrCode, bo.getOrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getClCode()), IcesOrder::getClCode, bo.getClCode());
        lqw.like(StringUtils.isNotBlank(bo.getOrName()), IcesOrder::getOrName, bo.getOrName());
        lqw.eq(StringUtils.isNotBlank(bo.getOrStat()), IcesOrder::getOrStat, bo.getOrStat());
        lqw.eq(bo.getOrPriority() != null, IcesOrder::getOrPriority, bo.getOrPriority());
        lqw.between(params.get("beginOrDeadline") != null && params.get("endOrDeadline") != null,
            IcesOrder::getOrDeadline ,params.get("beginOrDeadline"), params.get("endOrDeadline"));
        lqw.eq(bo.getOrDelete() != null, IcesOrder::getOrDelete, bo.getOrDelete());
        return lqw;
    }

    /**
     * 新增订单
     */
    @Override
    public IcesOrderVo insertByBo(IcesOrderBo bo) {
        String cMan = getLoginUsername();
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 填入创建信息
        bo.setOrCman(cMan);
        bo.setOrCdate(cDate);
        IcesOrder add = BeanUtil.toBean(bo, IcesOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOrId(add.getOrId());
        }
        return queryById(add.getOrId());
    }

    /**
     * 修改订单
     */
    @Override
    public Boolean updateByBo(IcesOrderBo bo) {
        String mMan = getLoginUsername();
        String mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 填入修改信息
        bo.setOrMman(mMan);
        bo.setOrMdate(mDate);
        IcesOrder update = BeanUtil.toBean(bo, IcesOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 获取当前用户名称
     * @return 用户名
     */
    private String getLoginUsername() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            return null;
        }
        return ObjectUtil.isNotNull(loginUser) ? loginUser.getUsername() : null;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除订单
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void updateStatus(IcesManufacturePlanBo icesManufacturePlanBo) {
        IcesOrderBo bo = new IcesOrderBo();
        bo.setOrCode(icesManufacturePlanBo.getMpCode());
        List<IcesOrderVo> icesOrderVos = queryList(bo);
        icesManufacturePlanBo.setMpId(icesOrderVos.get(0).getOrId());
        updateByBo(bo);
    }
}
