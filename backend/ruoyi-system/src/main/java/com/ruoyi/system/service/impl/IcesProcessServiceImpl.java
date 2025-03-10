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
import com.ruoyi.system.domain.bo.IcesProcessBo;
import com.ruoyi.system.domain.vo.IcesProcessVo;
import com.ruoyi.system.domain.IcesProcess;
import com.ruoyi.system.mapper.IcesProcessMapper;
import com.ruoyi.system.service.IIcesProcessService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 工艺流程Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-10
 */
@RequiredArgsConstructor
@Service
public class IcesProcessServiceImpl implements IIcesProcessService {

    private final IcesProcessMapper baseMapper;

    /**
     * 查询工艺流程
     */
    @Override
    public IcesProcessVo queryById(Long procId){
        return baseMapper.selectVoById(procId);
    }

    /**
     * 查询工艺流程列表
     */
    @Override
    public TableDataInfo<IcesProcessVo> queryPageList(IcesProcessBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProcess> lqw = buildQueryWrapper(bo);
        Page<IcesProcessVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询工艺流程列表
     */
    @Override
    public List<IcesProcessVo> queryList(IcesProcessBo bo) {
        LambdaQueryWrapper<IcesProcess> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProcess> buildQueryWrapper(IcesProcessBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProcess> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPrId() != null, IcesProcess::getPrId, bo.getPrId());
        lqw.like(StringUtils.isNotBlank(bo.getProcName()), IcesProcess::getProcName, bo.getProcName());
        lqw.eq(bo.getProcDelete() != null, IcesProcess::getProcDelete, bo.getProcDelete());
        return lqw;
    }

    /**
     * 新增工艺流程
     */
    @Override
    public Boolean insertByBo(IcesProcessBo bo) {
        IcesProcess add = BeanUtil.toBean(bo, IcesProcess.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setProcId(add.getProcId());
        }
        return flag;
    }

    /**
     * 修改工艺流程
     */
    @Override
    public Boolean updateByBo(IcesProcessBo bo) {
        IcesProcess update = BeanUtil.toBean(bo, IcesProcess.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProcess entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除工艺流程
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
