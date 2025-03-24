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
import com.ruoyi.system.domain.bo.IcesDeviceTaskPrevBo;
import com.ruoyi.system.domain.vo.IcesDeviceTaskPrevVo;
import com.ruoyi.system.domain.IcesDeviceTaskPrev;
import com.ruoyi.system.mapper.IcesDeviceTaskPrevMapper;
import com.ruoyi.system.service.IIcesDeviceTaskPrevService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 关联-设备任务的前序任务Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@RequiredArgsConstructor
@Service
public class IcesDeviceTaskPrevServiceImpl implements IIcesDeviceTaskPrevService {

    private final IcesDeviceTaskPrevMapper baseMapper;

    /**
     * 查询关联-设备任务的前序任务
     */
    @Override
    public IcesDeviceTaskPrevVo queryById(Long dtprId){
        return baseMapper.selectVoById(dtprId);
    }

    /**
     * 查询关联-设备任务的前序任务列表
     */
    @Override
    public TableDataInfo<IcesDeviceTaskPrevVo> queryPageList(IcesDeviceTaskPrevBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesDeviceTaskPrev> lqw = buildQueryWrapper(bo);
        Page<IcesDeviceTaskPrevVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询关联-设备任务的前序任务列表
     */
    @Override
    public List<IcesDeviceTaskPrevVo> queryList(IcesDeviceTaskPrevBo bo) {
        LambdaQueryWrapper<IcesDeviceTaskPrev> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesDeviceTaskPrev> buildQueryWrapper(IcesDeviceTaskPrevBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesDeviceTaskPrev> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDtIdCur() != null, IcesDeviceTaskPrev::getDtIdCur, bo.getDtIdCur());
        lqw.eq(bo.getDtIdPrev() != null, IcesDeviceTaskPrev::getDtIdPrev, bo.getDtIdPrev());
        return lqw;
    }

    /**
     * 新增关联-设备任务的前序任务
     */
    @Override
    public Boolean insertByBo(IcesDeviceTaskPrevBo bo) {
        IcesDeviceTaskPrev add = BeanUtil.toBean(bo, IcesDeviceTaskPrev.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDtprId(add.getDtprId());
        }
        return flag;
    }

    /**
     * 修改关联-设备任务的前序任务
     */
    @Override
    public Boolean updateByBo(IcesDeviceTaskPrevBo bo) {
        IcesDeviceTaskPrev update = BeanUtil.toBean(bo, IcesDeviceTaskPrev.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesDeviceTaskPrev entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除关联-设备任务的前序任务
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
