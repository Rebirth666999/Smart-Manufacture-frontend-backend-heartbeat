package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.IcesMaterialLedgerBo;
import com.ruoyi.system.domain.vo.IcesMaterialLedgerVo;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesMaterialLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesMaterialStockBo;
import com.ruoyi.system.domain.vo.IcesMaterialStockVo;
import com.ruoyi.system.domain.IcesMaterialStock;
import com.ruoyi.system.mapper.IcesMaterialStockMapper;
import com.ruoyi.system.service.IIcesMaterialStockService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 仓库原料库存Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@RequiredArgsConstructor
@Service
public class IcesMaterialStockServiceImpl implements IIcesMaterialStockService {

    private final IcesMaterialStockMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesMaterialLedgerService ledgerService;

    /**
     * 查询仓库原料库存
     */
    @Override
    public IcesMaterialStockVo queryById(Long mssId){
        return baseMapper.selectVoById(mssId);
    }

    /**
     * 查询仓库原料库存列表
     */
    @Override
    public TableDataInfo<IcesMaterialStockVo> queryPageList(IcesMaterialStockBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesMaterialStock> lqw = buildQueryWrapper(bo);
        Page<IcesMaterialStockVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询仓库原料库存列表
     */
    @Override
    public List<IcesMaterialStockVo> queryList(IcesMaterialStockBo bo) {
        LambdaQueryWrapper<IcesMaterialStock> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesMaterialStock> buildQueryWrapper(IcesMaterialStockBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesMaterialStock> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMssCode()), IcesMaterialStock::getMssCode, bo.getMssCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMsCode()), IcesMaterialStock::getMsCode, bo.getMsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMaCode()), IcesMaterialStock::getMaCode, bo.getMaCode());
        lqw.eq(bo.getMssDelete() != null, IcesMaterialStock::getMssDelete, bo.getMssDelete());
        return lqw;
    }

    /**
     * 新增仓库原料库存
     */
    @Override
    public Boolean insertByBo(IcesMaterialStockBo bo) {
        bo.setMssCode(codeService.insertByType("MaterialStock"));
        IcesMaterialStock add = BeanUtil.toBean(bo, IcesMaterialStock.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMssId(add.getMssId());
            // 找到台账
            IcesMaterialLedgerBo ledgerBo = new IcesMaterialLedgerBo();
            ledgerBo.setMaCode(add.getMaCode());
            List<IcesMaterialLedgerVo> ledgerVos = ledgerService.queryList(ledgerBo);
            assert (ledgerVos != null && !ledgerVos.isEmpty());
            ledgerBo.setMlId(ledgerVos.get(0).getMlId());
            ledgerBo.setMlCode(ledgerVos.get(0).getMlCode());
            // 修改库存量
            ledgerBo.setMlStock(ledgerVos.get(0).getMlStock() + add.getMssStock());
            ledgerService.updateByBo(ledgerBo);
        }
        return flag;
    }

    /**
     * 修改仓库原料库存
     */
    @Override
    public Boolean updateByBo(IcesMaterialStockBo bo) {
        IcesMaterialStock update = BeanUtil.toBean(bo, IcesMaterialStock.class);
        validEntityBeforeSave(update);
        // 找到原先记录
        IcesMaterialStockVo vo = queryById(update.getMssId());
        // 找到台账
        IcesMaterialLedgerBo ledgerBo = new IcesMaterialLedgerBo();
        ledgerBo.setMaCode(update.getMaCode());
        List<IcesMaterialLedgerVo> ledgerVos = ledgerService.queryList(ledgerBo);
        assert (ledgerVos != null && !ledgerVos.isEmpty());
        ledgerBo.setMlId(ledgerVos.get(0).getMlId());
        ledgerBo.setMlCode(ledgerVos.get(0).getMlCode());
        // 修改库存量
        ledgerBo.setMlStock(ledgerVos.get(0).getMlStock() - vo.getMssStock() + update.getMssStock());
        ledgerService.updateByBo(ledgerBo);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesMaterialStock entity){
        // 需要确保原料库存组合没有重复出现
        IcesMaterialStockBo bo = new IcesMaterialStockBo();
        bo.setMaCode(entity.getMaCode());
        bo.setMsCode(entity.getMsCode());
        List<IcesMaterialStockVo> vos = queryList(bo);
        if (!vos.isEmpty()) {
            // 只找到一个
            if (vos.size() == 1) {
                // ID一样，则校验通过
                if (vos.get(0).getMssId().equals(entity.getMssId())) return;
            }
            throw new RuntimeException("原料库存不能与已有重复");
        }
    }

    /**
     * 批量删除仓库原料库存
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        for (Long id : ids) {
            // 找到记录
            IcesMaterialStockVo vo = queryById(id);
            // 找到台账
            IcesMaterialLedgerBo ledgerBo = new IcesMaterialLedgerBo();
            ledgerBo.setMaCode(vo.getMaCode());
            List<IcesMaterialLedgerVo> ledgerVos = ledgerService.queryList(ledgerBo);
            assert (ledgerVos != null && !ledgerVos.isEmpty());
            ledgerBo.setMlId(ledgerVos.get(0).getMlId());
            ledgerBo.setMlCode(ledgerVos.get(0).getMlCode());
            // 修改库存量
            ledgerBo.setMlStock(ledgerVos.get(0).getMlStock() - vo.getMssStock());
            ledgerService.updateByBo(ledgerBo);
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
