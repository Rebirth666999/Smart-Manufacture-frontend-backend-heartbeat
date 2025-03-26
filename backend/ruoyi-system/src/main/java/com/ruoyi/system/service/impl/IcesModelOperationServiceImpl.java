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
import com.ruoyi.system.domain.bo.IcesModelOperationBo;
import com.ruoyi.system.domain.vo.IcesModelOperationVo;
import com.ruoyi.system.domain.IcesModelOperation;
import com.ruoyi.system.mapper.IcesModelOperationMapper;
import com.ruoyi.system.service.IIcesModelOperationService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备模型操作Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@RequiredArgsConstructor
@Service
public class IcesModelOperationServiceImpl implements IIcesModelOperationService {

    private final IcesModelOperationMapper baseMapper;

    /**
     * 查询设备模型操作
     */
    @Override
    public IcesModelOperationVo queryById(Long moId){
        return baseMapper.selectVoById(moId);
    }

    /**
     * 查询设备模型操作列表
     */
    @Override
    public TableDataInfo<IcesModelOperationVo> queryPageList(IcesModelOperationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesModelOperation> lqw = buildQueryWrapper(bo);
        Page<IcesModelOperationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备模型操作列表
     */
    @Override
    public List<IcesModelOperationVo> queryList(IcesModelOperationBo bo) {
        LambdaQueryWrapper<IcesModelOperation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesModelOperation> buildQueryWrapper(IcesModelOperationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesModelOperation> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getEmCode() != null, IcesModelOperation::getEmCode, bo.getEmCode());
        lqw.like(StringUtils.isNotBlank(bo.getMoName()), IcesModelOperation::getMoName, bo.getMoName());
        lqw.eq(bo.getMoDelete() != null, IcesModelOperation::getMoDelete, bo.getMoDelete());
        return lqw;
    }

    /**
     * 新增设备模型操作
     */
    @Override
    public Boolean insertByBo(IcesModelOperationBo bo) {
        IcesModelOperation add = BeanUtil.toBean(bo, IcesModelOperation.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMoId(add.getMoId());
        }
        return flag;
    }

    /**
     * 修改设备模型操作
     */
    @Override
    public Boolean updateByBo(IcesModelOperationBo bo) {
        IcesModelOperation update = BeanUtil.toBean(bo, IcesModelOperation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesModelOperation entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备模型操作
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
