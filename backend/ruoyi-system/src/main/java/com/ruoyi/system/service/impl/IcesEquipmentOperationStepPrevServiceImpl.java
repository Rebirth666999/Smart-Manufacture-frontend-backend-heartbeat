package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationStepPrevBo;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepPrevVo;
import com.ruoyi.system.domain.IcesEquipmentOperationStepPrev;
import com.ruoyi.system.mapper.IcesEquipmentOperationStepPrevMapper;
import com.ruoyi.system.service.IIcesEquipmentOperationStepPrevService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 关联-设备操作步骤的前序步骤Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentOperationStepPrevServiceImpl implements IIcesEquipmentOperationStepPrevService {

    private final IcesEquipmentOperationStepPrevMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询关联-设备操作步骤的前序步骤
     */
    @Override
    public IcesEquipmentOperationStepPrevVo queryById(Long eosprId){
        return baseMapper.selectVoById(eosprId);
    }

    /**
     * 查询关联-设备操作步骤的前序步骤列表
     */
    @Override
    public TableDataInfo<IcesEquipmentOperationStepPrevVo> queryPageList(IcesEquipmentOperationStepPrevBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentOperationStepPrev> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentOperationStepPrevVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询关联-设备操作步骤的前序步骤列表
     */
    @Override
    public List<IcesEquipmentOperationStepPrevVo> queryList(IcesEquipmentOperationStepPrevBo bo) {
        LambdaQueryWrapper<IcesEquipmentOperationStepPrev> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentOperationStepPrev> buildQueryWrapper(IcesEquipmentOperationStepPrevBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentOperationStepPrev> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getEosprCode()), IcesEquipmentOperationStepPrev::getEosprCode, bo.getEosprCode());
        lqw.eq(StringUtils.isNotBlank(bo.getEosCodeCur()), IcesEquipmentOperationStepPrev::getEosCodeCur, bo.getEosCodeCur());
        lqw.eq(StringUtils.isNotBlank(bo.getEosCodePrev()), IcesEquipmentOperationStepPrev::getEosCodePrev, bo.getEosCodePrev());
        lqw.eq(bo.getEosprDelete() != null, IcesEquipmentOperationStepPrev::getEosprDelete, bo.getEosprDelete());
        return lqw;
    }

    /**
     * 新增关联-设备操作步骤的前序步骤
     */
    @Override
    public Boolean insertByBo(IcesEquipmentOperationStepPrevBo bo) {
        bo.setEosprCode(codeService.insertByType("EquipmentOperationStepPrev"));
        IcesEquipmentOperationStepPrev add = BeanUtil.toBean(bo, IcesEquipmentOperationStepPrev.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEosprId(add.getEosprId());
        }
        return flag;
    }

    /**
     * 修改关联-设备操作步骤的前序步骤
     */
    @Override
    public Boolean updateByBo(IcesEquipmentOperationStepPrevBo bo) {
        IcesEquipmentOperationStepPrev update = BeanUtil.toBean(bo, IcesEquipmentOperationStepPrev.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentOperationStepPrev entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除关联-设备操作步骤的前序步骤
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
