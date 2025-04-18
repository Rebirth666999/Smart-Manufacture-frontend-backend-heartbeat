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
import com.ruoyi.system.domain.bo.IcesMaterialLedgerBo;
import com.ruoyi.system.domain.vo.IcesMaterialLedgerVo;
import com.ruoyi.system.domain.IcesMaterialLedger;
import com.ruoyi.system.mapper.IcesMaterialLedgerMapper;
import com.ruoyi.system.service.IIcesMaterialLedgerService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 原料台账Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@RequiredArgsConstructor
@Service
public class IcesMaterialLedgerServiceImpl implements IIcesMaterialLedgerService {

    private final IcesMaterialLedgerMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询原料台账
     */
    @Override
    public IcesMaterialLedgerVo queryById(Long mlId){
        return baseMapper.selectVoById(mlId);
    }

    /**
     * 查询原料台账列表
     */
    @Override
    public TableDataInfo<IcesMaterialLedgerVo> queryPageList(IcesMaterialLedgerBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesMaterialLedger> lqw = buildQueryWrapper(bo);
        Page<IcesMaterialLedgerVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询原料台账列表
     */
    @Override
    public List<IcesMaterialLedgerVo> queryList(IcesMaterialLedgerBo bo) {
        LambdaQueryWrapper<IcesMaterialLedger> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesMaterialLedger> buildQueryWrapper(IcesMaterialLedgerBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesMaterialLedger> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMlCode()), IcesMaterialLedger::getMlCode, bo.getMlCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMaCode()), IcesMaterialLedger::getMaCode, bo.getMaCode());
        lqw.eq(bo.getMlDelete() != null, IcesMaterialLedger::getMlDelete, bo.getMlDelete());
        return lqw;
    }

    /**
     * 新增原料台账
     */
    @Override
    public Boolean insertByBo(IcesMaterialLedgerBo bo) {
        bo.setMlCode(codeService.insertByType("MaterialLedger"));
        IcesMaterialLedger add = BeanUtil.toBean(bo, IcesMaterialLedger.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMlId(add.getMlId());
        }
        return flag;
    }

    /**
     * 修改原料台账
     */
    @Override
    public Boolean updateByBo(IcesMaterialLedgerBo bo) {
        IcesMaterialLedger update = BeanUtil.toBean(bo, IcesMaterialLedger.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesMaterialLedger entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除原料台账
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
