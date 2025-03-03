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
import com.ruoyi.system.domain.bo.IcesProductBo;
import com.ruoyi.system.domain.vo.IcesProductVo;
import com.ruoyi.system.domain.IcesProduct;
import com.ruoyi.system.mapper.IcesProductMapper;
import com.ruoyi.system.service.IIcesProductService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 产品Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-08
 */
@RequiredArgsConstructor
@Service
public class IcesProductServiceImpl implements IIcesProductService {

    private final IcesProductMapper baseMapper;

    /**
     * 查询产品
     */
    @Override
    public IcesProductVo queryById(Long prId){
        return baseMapper.selectVoById(prId);
    }

    /**
     * 查询产品列表
     */
    @Override
    public TableDataInfo<IcesProductVo> queryPageList(IcesProductBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProduct> lqw = buildQueryWrapper(bo);
        Page<IcesProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询产品列表
     */
    @Override
    public List<IcesProductVo> queryList(IcesProductBo bo) {
        LambdaQueryWrapper<IcesProduct> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProduct> buildQueryWrapper(IcesProductBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProduct> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPrName()), IcesProduct::getPrName, bo.getPrName());
        lqw.eq(bo.getPrDelete() != null, IcesProduct::getPrDelete, bo.getPrDelete());
        return lqw;
    }

    /**
     * 新增产品
     */
    @Override
    public Boolean insertByBo(IcesProductBo bo) {
        IcesProduct add = BeanUtil.toBean(bo, IcesProduct.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPrId(add.getPrId());
        }
        return flag;
    }

    /**
     * 修改产品
     */
    @Override
    public Boolean updateByBo(IcesProductBo bo) {
        IcesProduct update = BeanUtil.toBean(bo, IcesProduct.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProduct entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除产品
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
