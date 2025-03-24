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
import com.ruoyi.system.domain.bo.IcesDeviceTaskParamBo;
import com.ruoyi.system.domain.vo.IcesDeviceTaskParamVo;
import com.ruoyi.system.domain.IcesDeviceTaskParam;
import com.ruoyi.system.mapper.IcesDeviceTaskParamMapper;
import com.ruoyi.system.service.IIcesDeviceTaskParamService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备任务参数Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@RequiredArgsConstructor
@Service
public class IcesDeviceTaskParamServiceImpl implements IIcesDeviceTaskParamService {

    private final IcesDeviceTaskParamMapper baseMapper;

    /**
     * 查询设备任务参数
     */
    @Override
    public IcesDeviceTaskParamVo queryById(Long dtpaId){
        return baseMapper.selectVoById(dtpaId);
    }

    /**
     * 查询设备任务参数列表
     */
    @Override
    public TableDataInfo<IcesDeviceTaskParamVo> queryPageList(IcesDeviceTaskParamBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesDeviceTaskParam> lqw = buildQueryWrapper(bo);
        Page<IcesDeviceTaskParamVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备任务参数列表
     */
    @Override
    public List<IcesDeviceTaskParamVo> queryList(IcesDeviceTaskParamBo bo) {
        LambdaQueryWrapper<IcesDeviceTaskParam> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesDeviceTaskParam> buildQueryWrapper(IcesDeviceTaskParamBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesDeviceTaskParam> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getEospaId() != null, IcesDeviceTaskParam::getEospaId, bo.getEospaId());
        lqw.eq(bo.getDtId() != null, IcesDeviceTaskParam::getDtId, bo.getDtId());
        lqw.eq(bo.getDtpaDelete() != null, IcesDeviceTaskParam::getDtpaDelete, bo.getDtpaDelete());
        return lqw;
    }

    /**
     * 新增设备任务参数
     */
    @Override
    public Boolean insertByBo(IcesDeviceTaskParamBo bo) {
        IcesDeviceTaskParam add = BeanUtil.toBean(bo, IcesDeviceTaskParam.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDtpaId(add.getDtpaId());
        }
        return flag;
    }

    /**
     * 修改设备任务参数
     */
    @Override
    public Boolean updateByBo(IcesDeviceTaskParamBo bo) {
        IcesDeviceTaskParam update = BeanUtil.toBean(bo, IcesDeviceTaskParam.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesDeviceTaskParam entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备任务参数
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
