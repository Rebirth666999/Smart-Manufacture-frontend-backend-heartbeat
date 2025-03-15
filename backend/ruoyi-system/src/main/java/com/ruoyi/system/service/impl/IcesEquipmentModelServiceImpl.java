package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesEquipmentModelBo;
import com.ruoyi.system.domain.vo.IcesEquipmentModelVo;
import com.ruoyi.system.domain.IcesEquipmentModel;
import com.ruoyi.system.mapper.IcesEquipmentModelMapper;
import com.ruoyi.system.service.IIcesEquipmentModelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备模型Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentModelServiceImpl implements IIcesEquipmentModelService {

    private final IcesEquipmentModelMapper baseMapper;

    /**
     * 查询设备模型
     */
    @Override
    public IcesEquipmentModelVo queryById(Long emId){
        return baseMapper.selectVoById(emId);
    }

    /**
     * 查询设备模型列表
     */
    @Override
    public TableDataInfo<IcesEquipmentModelVo> queryPageList(IcesEquipmentModelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentModel> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentModelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 审核端查询设备模型列表
     */
    @Override
    public TableDataInfo<IcesEquipmentModelVo> queryReviewList(IcesEquipmentModelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentModel> lqw = buildQueryWrapper(bo);
        List<String> stats = new ArrayList<>();
        stats.add("2");  // 待审核
        stats.add("3");  // 审核中
        stats.add("6");  // 待审核（弃用）
        stats.add("7");  // 审核中（弃用）
        lqw.in(IcesEquipmentModel::getEmStat, stats);
        Page<IcesEquipmentModelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备模型列表
     */
    @Override
    public List<IcesEquipmentModelVo> queryList(IcesEquipmentModelBo bo) {
        LambdaQueryWrapper<IcesEquipmentModel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentModel> buildQueryWrapper(IcesEquipmentModelBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentModel> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getEmtId() != null, IcesEquipmentModel::getEmtId, bo.getEmtId());
        lqw.like(StringUtils.isNotBlank(bo.getEmName()), IcesEquipmentModel::getEmName, bo.getEmName());
        lqw.eq(StringUtils.isNotBlank(bo.getEmStat()), IcesEquipmentModel::getEmStat, bo.getEmStat());
        lqw.eq(bo.getEmDelete() != null, IcesEquipmentModel::getEmDelete, bo.getEmDelete());
        return lqw;
    }

    /**
     * 新增设备模型
     */
    @Override
    public Boolean insertByBo(IcesEquipmentModelBo bo) {
        IcesEquipmentModel add = BeanUtil.toBean(bo, IcesEquipmentModel.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEmId(add.getEmId());
        }
        return flag;
    }

    /**
     * 修改设备模型
     */
    @Override
    public Boolean updateByBo(IcesEquipmentModelBo bo) {
        IcesEquipmentModel update = BeanUtil.toBean(bo, IcesEquipmentModel.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentModel entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备模型
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
