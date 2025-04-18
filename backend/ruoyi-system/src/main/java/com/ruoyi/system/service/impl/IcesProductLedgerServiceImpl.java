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
import com.ruoyi.system.domain.bo.IcesProductLedgerBo;
import com.ruoyi.system.domain.vo.IcesProductLedgerVo;
import com.ruoyi.system.domain.IcesProductLedger;
import com.ruoyi.system.mapper.IcesProductLedgerMapper;
import com.ruoyi.system.service.IIcesProductLedgerService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 产品台账Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@RequiredArgsConstructor
@Service
public class IcesProductLedgerServiceImpl implements IIcesProductLedgerService {

    private final IcesProductLedgerMapper baseMapper;

    /**
     * 查询产品台账
     */
    @Override
    public IcesProductLedgerVo queryById(Long plId){
        return baseMapper.selectVoById(plId);
    }

    /**
     * 查询产品台账列表
     */
    @Override
    public TableDataInfo<IcesProductLedgerVo> queryPageList(IcesProductLedgerBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProductLedger> lqw = buildQueryWrapper(bo);
        Page<IcesProductLedgerVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询产品台账列表
     */
    @Override
    public List<IcesProductLedgerVo> queryList(IcesProductLedgerBo bo) {
        LambdaQueryWrapper<IcesProductLedger> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProductLedger> buildQueryWrapper(IcesProductLedgerBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProductLedger> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPlCode()), IcesProductLedger::getPlCode, bo.getPlCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrCode()), IcesProductLedger::getPrCode, bo.getPrCode());
        lqw.eq(bo.getPlDelete() != null, IcesProductLedger::getPlDelete, bo.getPlDelete());
        return lqw;
    }

    /**
     * 新增产品台账
     */
    @Override
    public Boolean insertByBo(IcesProductLedgerBo bo) {
        IcesProductLedger add = BeanUtil.toBean(bo, IcesProductLedger.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPlId(add.getPlId());
        }
        return flag;
    }

    /**
     * 修改产品台账
     */
    @Override
    public Boolean updateByBo(IcesProductLedgerBo bo) {
        IcesProductLedger update = BeanUtil.toBean(bo, IcesProductLedger.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProductLedger entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除产品台账
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
