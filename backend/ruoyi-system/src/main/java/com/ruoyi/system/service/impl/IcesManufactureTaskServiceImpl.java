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
import com.ruoyi.system.domain.bo.IcesManufactureTaskBo;
import com.ruoyi.system.domain.vo.IcesManufactureTaskVo;
import com.ruoyi.system.domain.IcesManufactureTask;
import com.ruoyi.system.mapper.IcesManufactureTaskMapper;
import com.ruoyi.system.service.IIcesManufactureTaskService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 生产任务Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@RequiredArgsConstructor
@Service
public class IcesManufactureTaskServiceImpl implements IIcesManufactureTaskService {

    private final IcesManufactureTaskMapper baseMapper;

    /**
     * 查询生产任务
     */
    @Override
    public IcesManufactureTaskVo queryById(Long mtId){
        return baseMapper.selectVoById(mtId);
    }

    /**
     * 查询生产任务列表
     */
    @Override
    public TableDataInfo<IcesManufactureTaskVo> queryPageList(IcesManufactureTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesManufactureTask> lqw = buildQueryWrapper(bo);
        Page<IcesManufactureTaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询生产任务列表
     */
    @Override
    public List<IcesManufactureTaskVo> queryList(IcesManufactureTaskBo bo) {
        LambdaQueryWrapper<IcesManufactureTask> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesManufactureTask> buildQueryWrapper(IcesManufactureTaskBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesManufactureTask> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMpId() != null, IcesManufactureTask::getMpId, bo.getMpId());
        lqw.eq(bo.getArId() != null, IcesManufactureTask::getArId, bo.getArId());
        lqw.eq(StringUtils.isNotBlank(bo.getMtStat()), IcesManufactureTask::getMtStat, bo.getMtStat());
        lqw.eq(bo.getMtPriority() != null, IcesManufactureTask::getMtPriority, bo.getMtPriority());
        lqw.eq(bo.getMtDelete() != null, IcesManufactureTask::getMtDelete, bo.getMtDelete());
        return lqw;
    }

    /**
     * 新增生产任务
     */
    @Override
    public Boolean insertByBo(IcesManufactureTaskBo bo) {
        IcesManufactureTask add = BeanUtil.toBean(bo, IcesManufactureTask.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMtId(add.getMtId());
        }
        return flag;
    }

    /**
     * 修改生产任务
     */
    @Override
    public Boolean updateByBo(IcesManufactureTaskBo bo) {
        IcesManufactureTask update = BeanUtil.toBean(bo, IcesManufactureTask.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesManufactureTask entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除生产任务
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
