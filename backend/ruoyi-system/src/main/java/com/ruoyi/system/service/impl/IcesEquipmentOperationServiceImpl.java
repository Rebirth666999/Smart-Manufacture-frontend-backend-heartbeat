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
import com.ruoyi.system.domain.bo.IcesEquipmentOperationBo;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationVo;
import com.ruoyi.system.domain.IcesEquipmentOperation;
import com.ruoyi.system.mapper.IcesEquipmentOperationMapper;
import com.ruoyi.system.service.IIcesEquipmentOperationService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备操作Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-02
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentOperationServiceImpl implements IIcesEquipmentOperationService {

    private final IcesEquipmentOperationMapper baseMapper;

    /**
     * 查询设备操作
     */
    @Override
    public IcesEquipmentOperationVo queryById(Long eoId){
        return baseMapper.selectVoById(eoId);
    }

    /**
     * 查询设备操作列表
     */
    @Override
    public TableDataInfo<IcesEquipmentOperationVo> queryPageList(IcesEquipmentOperationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentOperation> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentOperationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备操作列表
     */
    @Override
    public List<IcesEquipmentOperationVo> queryList(IcesEquipmentOperationBo bo) {
        LambdaQueryWrapper<IcesEquipmentOperation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentOperation> buildQueryWrapper(IcesEquipmentOperationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentOperation> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMoId() != null, IcesEquipmentOperation::getMoId, bo.getMoId());
        lqw.eq(bo.getEqId() != null, IcesEquipmentOperation::getEqId, bo.getEqId());
        lqw.eq(bo.getEoDelete() != null, IcesEquipmentOperation::getEoDelete, bo.getEoDelete());
        return lqw;
    }

    /**
     * 新增设备操作
     */
    @Override
    public Boolean insertByBo(IcesEquipmentOperationBo bo) {
        IcesEquipmentOperation add = BeanUtil.toBean(bo, IcesEquipmentOperation.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEoId(add.getEoId());
        }
        return flag;
    }

    /**
     * 修改设备操作
     */
    @Override
    public Boolean updateByBo(IcesEquipmentOperationBo bo) {
        IcesEquipmentOperation update = BeanUtil.toBean(bo, IcesEquipmentOperation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentOperation entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备操作
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
