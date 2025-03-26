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
import com.ruoyi.system.domain.bo.IcesEquipmentAtomOperationBo;
import com.ruoyi.system.domain.vo.IcesEquipmentAtomOperationVo;
import com.ruoyi.system.domain.IcesEquipmentAtomOperation;
import com.ruoyi.system.mapper.IcesEquipmentAtomOperationMapper;
import com.ruoyi.system.service.IIcesEquipmentAtomOperationService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备原子操作Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-02
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentAtomOperationServiceImpl implements IIcesEquipmentAtomOperationService {

    private final IcesEquipmentAtomOperationMapper baseMapper;

    /**
     * 查询设备原子操作
     */
    @Override
    public IcesEquipmentAtomOperationVo queryById(Long eaoId){
        return baseMapper.selectVoById(eaoId);
    }

    /**
     * 查询设备原子操作列表
     */
    @Override
    public TableDataInfo<IcesEquipmentAtomOperationVo> queryPageList(IcesEquipmentAtomOperationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentAtomOperation> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentAtomOperationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备原子操作列表
     */
    @Override
    public List<IcesEquipmentAtomOperationVo> queryList(IcesEquipmentAtomOperationBo bo) {
        LambdaQueryWrapper<IcesEquipmentAtomOperation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentAtomOperation> buildQueryWrapper(IcesEquipmentAtomOperationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentAtomOperation> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getEqCode() != null, IcesEquipmentAtomOperation::getEqCode, bo.getEqCode());
        lqw.eq(bo.getEaoDelete() != null, IcesEquipmentAtomOperation::getEaoDelete, bo.getEaoDelete());
        return lqw;
    }

    /**
     * 新增设备原子操作
     */
    @Override
    public Boolean insertByBo(IcesEquipmentAtomOperationBo bo) {
        IcesEquipmentAtomOperation add = BeanUtil.toBean(bo, IcesEquipmentAtomOperation.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEaoId(add.getEaoId());
        }
        return flag;
    }

    /**
     * 修改设备原子操作
     */
    @Override
    public Boolean updateByBo(IcesEquipmentAtomOperationBo bo) {
        IcesEquipmentAtomOperation update = BeanUtil.toBean(bo, IcesEquipmentAtomOperation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentAtomOperation entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备原子操作
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
