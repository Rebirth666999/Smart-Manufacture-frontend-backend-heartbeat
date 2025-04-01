package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.IcesEquipmentModelBo;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesEquipmentBo;
import com.ruoyi.system.domain.vo.IcesEquipmentVo;
import com.ruoyi.system.domain.IcesEquipment;
import com.ruoyi.system.mapper.IcesEquipmentMapper;
import com.ruoyi.system.service.IIcesEquipmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentServiceImpl implements IIcesEquipmentService {

    private final IcesEquipmentMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询设备
     */
    @Override
    public IcesEquipmentVo queryById(Long eqId){
        return baseMapper.selectVoById(eqId);
    }

    /**
     * 查询设备列表
     */
    @Override
    public TableDataInfo<IcesEquipmentVo> queryPageList(IcesEquipmentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipment> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }



    @Override
    public TableDataInfo<IcesEquipmentVo> queryReviewList(IcesEquipmentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipment> lqw = buildQueryWrapper(bo);
        List<String> stats = new ArrayList<>();
        stats.add("2");  // 待审核
        stats.add("3");  // 审核中
        stats.add("b");  // 待审核（弃用）
        stats.add("c");  // 审核中（弃用）
        lqw.in(IcesEquipment::getEmStat, stats);
        Page<IcesEquipmentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备列表
     */
    @Override
    public List<IcesEquipmentVo> queryList(IcesEquipmentBo bo) {
        LambdaQueryWrapper<IcesEquipment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipment> buildQueryWrapper(IcesEquipmentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipment> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getEqCode()), IcesEquipment::getEqCode, bo.getEqCode());
        lqw.eq(StringUtils.isNotBlank(bo.getArCode()), IcesEquipment::getArCode, bo.getArCode());
        lqw.eq(StringUtils.isNotBlank(bo.getEmCode()), IcesEquipment::getEmCode, bo.getEmCode());
        lqw.like(StringUtils.isNotBlank(bo.getEqName()), IcesEquipment::getEqName, bo.getEqName());
        lqw.eq(StringUtils.isNotBlank(bo.getEqStat()), IcesEquipment::getEqStat, bo.getEqStat());
        lqw.eq(bo.getEqDelete() != null, IcesEquipment::getEqDelete, bo.getEqDelete());
        return lqw;
    }

    /**
     * 新增设备
     */
    @Override
    public Boolean insertByBo(IcesEquipmentBo bo) {
        bo.setEqCode(codeService.insertByType("Equipment"));
        IcesEquipment add = BeanUtil.toBean(bo, IcesEquipment.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEqCode(add.getEqCode());
        }
        return flag;
    }

    /**
     * 修改设备
     */
    @Override
    public Boolean updateByBo(IcesEquipmentBo bo) {
        IcesEquipment update = BeanUtil.toBean(bo, IcesEquipment.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipment entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
