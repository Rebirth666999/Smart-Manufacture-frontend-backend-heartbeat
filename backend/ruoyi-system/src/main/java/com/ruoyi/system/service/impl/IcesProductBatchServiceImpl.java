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
import com.ruoyi.system.domain.bo.IcesProductBatchBo;
import com.ruoyi.system.domain.vo.IcesProductBatchVo;
import com.ruoyi.system.domain.IcesProductBatch;
import com.ruoyi.system.mapper.IcesProductBatchMapper;
import com.ruoyi.system.service.IIcesProductBatchService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 实际产品批次编码Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@RequiredArgsConstructor
@Service
public class IcesProductBatchServiceImpl implements IIcesProductBatchService {

    private final IcesProductBatchMapper baseMapper;

    /**
     * 查询实际产品批次编码
     */
    @Override
    public IcesProductBatchVo queryById(Long pbId){
        return baseMapper.selectVoById(pbId);
    }

    /**
     * 查询实际产品批次编码列表
     */
    @Override
    public TableDataInfo<IcesProductBatchVo> queryPageList(IcesProductBatchBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProductBatch> lqw = buildQueryWrapper(bo);
        Page<IcesProductBatchVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询实际产品批次编码列表
     */
    @Override
    public List<IcesProductBatchVo> queryList(IcesProductBatchBo bo) {
        LambdaQueryWrapper<IcesProductBatch> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProductBatch> buildQueryWrapper(IcesProductBatchBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProductBatch> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPbCode()), IcesProductBatch::getPbCode, bo.getPbCode());
        lqw.eq(StringUtils.isNotBlank(bo.getOdCode()), IcesProductBatch::getOdCode, bo.getOdCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPbBatch()), IcesProductBatch::getPbBatch, bo.getPbBatch());
        lqw.eq(bo.getPbDelete() != null, IcesProductBatch::getPbDelete, bo.getPbDelete());
        return lqw;
    }

    /**
     * 新增实际产品批次编码
     */
    @Override
    public Boolean insertByBo(IcesProductBatchBo bo) {
        IcesProductBatch add = BeanUtil.toBean(bo, IcesProductBatch.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPbId(add.getPbId());
        }
        return flag;
    }

    /**
     * 修改实际产品批次编码
     */
    @Override
    public Boolean updateByBo(IcesProductBatchBo bo) {
        IcesProductBatch update = BeanUtil.toBean(bo, IcesProductBatch.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProductBatch entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除实际产品批次编码
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
