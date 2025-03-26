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
import com.ruoyi.system.domain.bo.IcesDeviceTaskBo;
import com.ruoyi.system.domain.vo.IcesDeviceTaskVo;
import com.ruoyi.system.domain.IcesDeviceTask;
import com.ruoyi.system.mapper.IcesDeviceTaskMapper;
import com.ruoyi.system.service.IIcesDeviceTaskService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备任务Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-19
 */
@RequiredArgsConstructor
@Service
public class IcesDeviceTaskServiceImpl implements IIcesDeviceTaskService {

    private final IcesDeviceTaskMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询设备任务
     */
    @Override
    public IcesDeviceTaskVo queryById(Long dtId){
        return baseMapper.selectVoById(dtId);
    }

    /**
     * 查询设备任务列表
     */
    @Override
    public TableDataInfo<IcesDeviceTaskVo> queryPageList(IcesDeviceTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesDeviceTask> lqw = buildQueryWrapper(bo);
        Page<IcesDeviceTaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备任务列表
     */
    @Override
    public List<IcesDeviceTaskVo> queryList(IcesDeviceTaskBo bo) {
        LambdaQueryWrapper<IcesDeviceTask> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesDeviceTask> buildQueryWrapper(IcesDeviceTaskBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesDeviceTask> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getDtCode()), IcesDeviceTask::getDtCode, bo.getDtCode());
        lqw.eq(StringUtils.isNotBlank(bo.getEqCode()), IcesDeviceTask::getEqCode, bo.getEqCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMtCode()), IcesDeviceTask::getMtCode, bo.getMtCode());
        lqw.eq(StringUtils.isNotBlank(bo.getEoCode()), IcesDeviceTask::getEoCode, bo.getEoCode());
        lqw.eq(StringUtils.isNotBlank(bo.getDtStat()), IcesDeviceTask::getDtStat, bo.getDtStat());
        lqw.eq(bo.getDtDelete() != null, IcesDeviceTask::getDtDelete, bo.getDtDelete());
        return lqw;
    }

    /**
     * 新增设备任务
     */
    @Override
    public Boolean insertByBo(IcesDeviceTaskBo bo) {
        bo.setDtCode(codeService.insertByType("DeviceTask"));
        IcesDeviceTask add = BeanUtil.toBean(bo, IcesDeviceTask.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDtId(add.getDtId());
        }
        return flag;
    }

    /**
     * 修改设备任务
     */
    @Override
    public Boolean updateByBo(IcesDeviceTaskBo bo) {
        IcesDeviceTask update = BeanUtil.toBean(bo, IcesDeviceTask.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesDeviceTask entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备任务
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
