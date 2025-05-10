package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.IcesExceptionRecordBo;
import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesExceptionRecordService;
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
    private final IIcesExceptionRecordService exceptionRecordService;

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
        // 检查编码
        if (StringUtils.isBlank(bo.getOrCode())) {
            bo.setOrCode(codeService.insertByType("Order"));
        } else {
            codeService.checkCode("Order", bo.getOrCode());
        }
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
    public IcesOrderVo updateByBo(IcesOrderBo bo) {
        // 先找到原先信息
        IcesOrderVo orgn = queryById(bo.getOrId());
        // 填入修改信息
        String mMan = getLoginUsername();
        String mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        bo.setOrMman(mMan);
        bo.setOrMdate(mDate);
        if (orgn.getOrCodeOrgn() == null && StringUtils.isNotBlank(bo.getOrCodeOrgn())) {
            // 之前没有原订单号，现在有，说明修改了订单的信息
            // 上报异常
            IcesExceptionRecordBo exceptionRecordBo = new IcesExceptionRecordBo();
            exceptionRecordBo.setExsCode("ExceptionSource-00002");  // 异常源：信息系统
            exceptionRecordBo.setExCode("Exception-00002");  // 异常：订单修改
            exceptionRecordBo.setExrUserReport("信息系统");  // 上报人
            exceptionRecordBo.setExrStat("4");  // 直接确认为异常
            exceptionRecordBo.setExrLevel("2");  // 异常等级：严重
            exceptionRecordBo.setExrDuration(0L);  // 持续时间：无（因为是立即上报）
            exceptionRecordBo.setExrImpactFactor(0.5F);  // 影响因子
            exceptionRecordBo.setExrImpactLevel("2");  // 影响等级：严重
            // 参数：填入订单信息
            Map<String, String> params = new HashMap<>();
            params.put("orCodeOrgn", bo.getOrCodeOrgn());
            params.put("orCode", bo.getOrCode() + "-D");
            exceptionRecordBo.setExrParam(JSONUtil.toJsonStr(params));
            // 编写描述
            exceptionRecordBo.setExrDesc("订单 " + bo.getOrCodeOrgn() + " 发生修改，时间 " + mDate);
            exceptionRecordService.insertByBo(exceptionRecordBo);
            // 立即启动异常处理
            exceptionRecordService.startLifecycle(exceptionRecordBo);

            // 原先实体状态变成已修改
            IcesOrder update = new IcesOrder();
            update.setOrId(bo.getOrId());
            update.setOrStat("d");
            validEntityBeforeSave(update);
            baseMapper.updateById(update);
            // 新的实体变为新订单信息
            // 抹掉ID
            bo.setOrId(null);
            // 设置编码
            bo.setOrCode(bo.getOrCode() + "-D");
            // 状态为待审核（修改）
            bo.setOrStat("8");
            return insertByBo(bo);
        } else {
            // 普通修改
            IcesOrder update = BeanUtil.toBean(bo, IcesOrder.class);
            validEntityBeforeSave(update);
            baseMapper.updateById(update);
            return queryById(update.getOrId());
        }
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

    /**
     * 所有生产计划完成，更新订单
     *
     * @param icesManufacturePlanBo 作为参照的生产计划信息
     */
    @Override
    public void updateStatus(IcesManufacturePlanBo icesManufacturePlanBo) {
        // 找到订单
        IcesOrderBo bo = new IcesOrderBo();
        bo.setOrCode(icesManufacturePlanBo.getOrCode());
        List<IcesOrderVo> icesOrderVos = queryList(bo);
        // 设置ID、状态
        bo.setOrId(icesOrderVos.get(0).getOrId());
        bo.setOrCode(null);
        bo.setOrStat("6");
        updateByBo(bo);
    }
}
