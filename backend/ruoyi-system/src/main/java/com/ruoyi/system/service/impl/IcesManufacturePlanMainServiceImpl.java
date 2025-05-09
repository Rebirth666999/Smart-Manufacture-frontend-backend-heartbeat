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
import com.ruoyi.system.domain.IcesManufacturePlan;
import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.domain.bo.IcesOrderBo;
import com.ruoyi.system.domain.bo.IcesOrderDemandBo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.domain.vo.IcesOrderDemandVo;
import com.ruoyi.system.domain.vo.IcesOrderVo;
import com.ruoyi.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesManufacturePlanMainBo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanMainVo;
import com.ruoyi.system.domain.IcesManufacturePlanMain;
import com.ruoyi.system.mapper.IcesManufacturePlanMainMapper;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 生产计划(主)Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-25
 */
@RequiredArgsConstructor
@Service
public class IcesManufacturePlanMainServiceImpl implements IIcesManufacturePlanMainService {

    private final IcesManufacturePlanMainMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesManufacturePlanService manufacturePlanService;
    private final IIcesOrderDemandService orderDemandService;
    private final IIcesOrderService orderService;

    /**
     * 查询生产计划(主)
     */
    @Override
    public IcesManufacturePlanMainVo queryById(Long mpmId){
        return baseMapper.selectVoById(mpmId);
    }

    /**
     * 查询生产计划(主)列表
     */
    @Override
    public TableDataInfo<IcesManufacturePlanMainVo> queryPageList(IcesManufacturePlanMainBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesManufacturePlanMain> lqw = buildQueryWrapper(bo);
        Page<IcesManufacturePlanMainVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询审核用的生产计划(主)列表
     */
    @Override
    public TableDataInfo<IcesManufacturePlanMainVo> queryReviewList(IcesManufacturePlanMainBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesManufacturePlanMain> lqw = buildQueryWrapper(bo);
        List<String> stats = new ArrayList<>();
        stats.add("2");  // 待审核
        stats.add("3");  // 审核中
        stats.add("a");  // 待审核（弃用）
        stats.add("b");  // 审核中（弃用）
        stats.add("7");  // 待审核（修改）
        stats.add("8");  // 待审核（修改）
        lqw.in(IcesManufacturePlanMain::getMpmStat, stats);
        Page<IcesManufacturePlanMainVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询生产计划(主)列表
     */
    @Override
    public List<IcesManufacturePlanMainVo> queryList(IcesManufacturePlanMainBo bo) {
        LambdaQueryWrapper<IcesManufacturePlanMain> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesManufacturePlanMain> buildQueryWrapper(IcesManufacturePlanMainBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesManufacturePlanMain> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMpmCode()), IcesManufacturePlanMain::getMpmCode, bo.getMpmCode());
        lqw.eq(StringUtils.isNotBlank(bo.getOrCode()), IcesManufacturePlanMain::getOrCode, bo.getOrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMpmStat()), IcesManufacturePlanMain::getMpmStat, bo.getMpmStat());
        lqw.eq(bo.getMpmDelete() != null, IcesManufacturePlanMain::getMpmDelete, bo.getMpmDelete());
        return lqw;
    }

    /**
     * 新增生产计划(主)
     */
    @Override
    public IcesManufacturePlanMainVo insertByBo(IcesManufacturePlanMainBo bo) {
        if (StringUtils.isBlank(bo.getMpmCode())) {
            bo.setMpmCode(codeService.insertByType("ManufacturePlanMain"));
        } else {
            codeService.checkCode("ManufacturePlanMain", bo.getMpmCode());
        }
        String cMan = getLoginUsername();
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        bo.setMpmCman(cMan);
        bo.setMpmCdate(cDate);
        IcesManufacturePlanMain add = BeanUtil.toBean(bo, IcesManufacturePlanMain.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMpmId(add.getMpmId());
            // 根据订单的产品需求，自动加入生产计划详细
            // 查找订单产品需求
            IcesOrderDemandBo orderDemandBo = new IcesOrderDemandBo();
            orderDemandBo.setOrCode(bo.getOrCode());
            List<IcesOrderDemandVo> orderDemandVos = orderDemandService.queryList(orderDemandBo);
            // 查找订单
            IcesOrderBo orderBo = new IcesOrderBo();
            orderBo.setOrCode(bo.getOrCode());
            List<IcesOrderVo> orderVos = orderService.queryList(orderBo);
            assert orderVos.size() == 1;
            // 自动生成计划详细信息
            for (IcesOrderDemandVo orderDemandVo : orderDemandVos) {
                IcesManufacturePlanBo manufacturePlanBo = new IcesManufacturePlanBo();
                manufacturePlanBo.setOrCode(bo.getOrCode());
                manufacturePlanBo.setMpmCode(bo.getMpmCode());
                manufacturePlanBo.setMpEndPlan(bo.getMpmEndPlan());
                manufacturePlanBo.setOdCode(orderDemandVo.getOdCode());
                manufacturePlanBo.setMpQtyPlan(orderDemandVo.getOdDemand());
                manufacturePlanBo.setMpPriority(orderVos.get(0).getOrPriority());
                manufacturePlanBo.setMpStat("1");
                manufacturePlanService.insertByBo(manufacturePlanBo);
            }
        }
        return queryById(add.getMpmId());
    }

    /**
     * 修改生产计划(主)
     */
    @Override
    public Boolean updateByBo(IcesManufacturePlanMainBo bo) {
        String mMan = getLoginUsername();
        String mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        bo.setMpmMman(mMan);
        bo.setMpmMdate(mDate);
        IcesManufacturePlanMain update = BeanUtil.toBean(bo, IcesManufacturePlanMain.class);
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
    private void validEntityBeforeSave(IcesManufacturePlanMain entity){
        // 新增的生产计划不能和已有的重复
        if (entity.getMpmId() == null) {
            IcesManufacturePlanMainBo bo = new IcesManufacturePlanMainBo();
            bo.setOrCode(entity.getOrCode());
            List<IcesManufacturePlanMainVo> vos = queryList(bo);
            if (!vos.isEmpty()) {
                throw new RuntimeException("对应订单已存在生产计划");
            }
        }
    }

    /**
     * 批量删除生产计划(主)
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
