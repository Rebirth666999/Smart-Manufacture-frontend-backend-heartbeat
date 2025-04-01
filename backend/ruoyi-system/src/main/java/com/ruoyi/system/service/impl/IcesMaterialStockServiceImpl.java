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
import com.ruoyi.system.domain.bo.IcesMaterialStockBo;
import com.ruoyi.system.domain.vo.IcesMaterialStockVo;
import com.ruoyi.system.domain.IcesMaterialStock;
import com.ruoyi.system.mapper.IcesMaterialStockMapper;
import com.ruoyi.system.service.IIcesMaterialStockService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 关联-车间原料库存Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-01
 */
@RequiredArgsConstructor
@Service
public class IcesMaterialStockServiceImpl implements IIcesMaterialStockService {

    private final IcesMaterialStockMapper baseMapper;

    /**
     * 查询关联-车间原料库存
     */
    @Override
    public IcesMaterialStockVo queryById(Long msId){
        return baseMapper.selectVoById(msId);
    }

    /**
     * 查询关联-车间原料库存列表
     */
    @Override
    public TableDataInfo<IcesMaterialStockVo> queryPageList(IcesMaterialStockBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesMaterialStock> lqw = buildQueryWrapper(bo);
        Page<IcesMaterialStockVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询关联-车间原料库存列表
     */
    @Override
    public List<IcesMaterialStockVo> queryList(IcesMaterialStockBo bo) {
        LambdaQueryWrapper<IcesMaterialStock> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesMaterialStock> buildQueryWrapper(IcesMaterialStockBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesMaterialStock> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMsCode()), IcesMaterialStock::getMsCode, bo.getMsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getStCode()), IcesMaterialStock::getStCode, bo.getStCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMaCode()), IcesMaterialStock::getMaCode, bo.getMaCode());
        lqw.eq(bo.getMsDelete() != null, IcesMaterialStock::getMsDelete, bo.getMsDelete());
        return lqw;
    }

    /**
     * 新增关联-车间原料库存
     */
    @Override
    public Boolean insertByBo(IcesMaterialStockBo bo) {
        IcesMaterialStock add = BeanUtil.toBean(bo, IcesMaterialStock.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMsId(add.getMsId());
        }
        return flag;
    }

    /**
     * 修改关联-车间原料库存
     */
    @Override
    public Boolean updateByBo(IcesMaterialStockBo bo) {
        IcesMaterialStock update = BeanUtil.toBean(bo, IcesMaterialStock.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesMaterialStock entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除关联-车间原料库存
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
