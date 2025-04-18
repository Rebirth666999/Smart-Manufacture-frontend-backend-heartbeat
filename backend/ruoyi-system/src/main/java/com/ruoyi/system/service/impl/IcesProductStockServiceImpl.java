package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.IcesMaterialLedgerBo;
import com.ruoyi.system.domain.bo.IcesMaterialStockBo;
import com.ruoyi.system.domain.bo.IcesProductLedgerBo;
import com.ruoyi.system.domain.vo.IcesMaterialLedgerVo;
import com.ruoyi.system.domain.vo.IcesMaterialStockVo;
import com.ruoyi.system.domain.vo.IcesProductLedgerVo;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesProductLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesProductStockBo;
import com.ruoyi.system.domain.vo.IcesProductStockVo;
import com.ruoyi.system.domain.IcesProductStock;
import com.ruoyi.system.mapper.IcesProductStockMapper;
import com.ruoyi.system.service.IIcesProductStockService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 仓库产品库存Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@RequiredArgsConstructor
@Service
public class IcesProductStockServiceImpl implements IIcesProductStockService {

    private final IcesProductStockMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesProductLedgerService ledgerService;

    /**
     * 查询仓库产品库存
     */
    @Override
    public IcesProductStockVo queryById(Long pssId){
        return baseMapper.selectVoById(pssId);
    }

    /**
     * 查询仓库产品库存列表
     */
    @Override
    public TableDataInfo<IcesProductStockVo> queryPageList(IcesProductStockBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProductStock> lqw = buildQueryWrapper(bo);
        Page<IcesProductStockVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询仓库产品库存列表
     */
    @Override
    public List<IcesProductStockVo> queryList(IcesProductStockBo bo) {
        LambdaQueryWrapper<IcesProductStock> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProductStock> buildQueryWrapper(IcesProductStockBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProductStock> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPssCode()), IcesProductStock::getPssCode, bo.getPssCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrsCode()), IcesProductStock::getPrsCode, bo.getPrsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrCode()), IcesProductStock::getPrCode, bo.getPrCode());
        lqw.eq(bo.getPssDelete() != null, IcesProductStock::getPssDelete, bo.getPssDelete());
        return lqw;
    }

    /**
     * 新增仓库产品库存
     */
    @Override
    public Boolean insertByBo(IcesProductStockBo bo) {
        bo.setPssCode(codeService.insertByType("ProductStock"));
        IcesProductStock add = BeanUtil.toBean(bo, IcesProductStock.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPssId(add.getPssId());
            // 找到台账
            IcesProductLedgerBo ledgerBo = new IcesProductLedgerBo();
            ledgerBo.setPrCode(add.getPrCode());
            List<IcesProductLedgerVo> ledgerVos = ledgerService.queryList(ledgerBo);
            assert (ledgerVos != null && !ledgerVos.isEmpty());
            ledgerBo.setPlId(ledgerVos.get(0).getPlId());
            ledgerBo.setPlCode(ledgerVos.get(0).getPlCode());
            // 修改库存量
            ledgerBo.setPlStock(ledgerVos.get(0).getPlStock() + add.getPssStock());
            ledgerService.updateByBo(ledgerBo);
        }
        return flag;
    }

    /**
     * 修改仓库产品库存
     */
    @Override
    public Boolean updateByBo(IcesProductStockBo bo) {
        IcesProductStock update = BeanUtil.toBean(bo, IcesProductStock.class);
        validEntityBeforeSave(update);
        // 找到原先记录
        IcesProductStockVo vo = queryById(update.getPssId());
        // 找到台账
        IcesProductLedgerBo ledgerBo = new IcesProductLedgerBo();
        ledgerBo.setPrCode(update.getPrCode());
        List<IcesProductLedgerVo> ledgerVos = ledgerService.queryList(ledgerBo);
        assert (ledgerVos != null && !ledgerVos.isEmpty());
        ledgerBo.setPlId(ledgerVos.get(0).getPlId());
        ledgerBo.setPlCode(ledgerVos.get(0).getPlCode());
        // 修改库存量
        ledgerBo.setPlStock(ledgerVos.get(0).getPlStock() - vo.getPssStock() + update.getPssStock());
        ledgerService.updateByBo(ledgerBo);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProductStock entity){
        // 需要确保产品库存组合没有重复出现
        IcesProductStockBo bo = new IcesProductStockBo();
        bo.setPrCode(entity.getPrCode());
        bo.setPrsCode(entity.getPrsCode());
        List<IcesProductStockVo> vos = queryList(bo);
        if (!vos.isEmpty()) {
            // 只找到一个
            if (vos.size() == 1) {
                // ID一样，则校验通过
                if (vos.get(0).getPssId().equals(entity.getPssId())) return;
            }
            throw new RuntimeException("产品库存不能与已有重复");
        }
    }

    /**
     * 批量删除仓库产品库存
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        for (Long id : ids) {
            // 找到记录
            IcesProductStockVo vo = queryById(id);
            // 找到台账
            IcesProductLedgerBo ledgerBo = new IcesProductLedgerBo();
            ledgerBo.setPrCode(vo.getPrCode());
            List<IcesProductLedgerVo> ledgerVos = ledgerService.queryList(ledgerBo);
            assert (ledgerVos != null && !ledgerVos.isEmpty());
            ledgerBo.setPlId(ledgerVos.get(0).getPlId());
            ledgerBo.setPlCode(ledgerVos.get(0).getPlCode());
            // 修改库存量
            ledgerBo.setPlStock(ledgerVos.get(0).getPlStock() - vo.getPssStock());
            ledgerService.updateByBo(ledgerBo);
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
