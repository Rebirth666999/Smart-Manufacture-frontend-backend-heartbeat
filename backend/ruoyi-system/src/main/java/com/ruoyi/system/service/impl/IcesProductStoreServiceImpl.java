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
import com.ruoyi.system.domain.bo.IcesProductStoreBo;
import com.ruoyi.system.domain.vo.IcesProductStoreVo;
import com.ruoyi.system.domain.IcesProductStore;
import com.ruoyi.system.mapper.IcesProductStoreMapper;
import com.ruoyi.system.service.IIcesProductStoreService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 产品仓库Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@RequiredArgsConstructor
@Service
public class IcesProductStoreServiceImpl implements IIcesProductStoreService {

    private final IcesProductStoreMapper baseMapper;

    /**
     * 查询产品仓库
     */
    @Override
    public IcesProductStoreVo queryById(Long prsId){
        return baseMapper.selectVoById(prsId);
    }

    /**
     * 查询产品仓库列表
     */
    @Override
    public TableDataInfo<IcesProductStoreVo> queryPageList(IcesProductStoreBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProductStore> lqw = buildQueryWrapper(bo);
        Page<IcesProductStoreVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询产品仓库列表
     */
    @Override
    public List<IcesProductStoreVo> queryList(IcesProductStoreBo bo) {
        LambdaQueryWrapper<IcesProductStore> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProductStore> buildQueryWrapper(IcesProductStoreBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProductStore> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPrsCode()), IcesProductStore::getPrsCode, bo.getPrsCode());
        lqw.like(StringUtils.isNotBlank(bo.getPrsName()), IcesProductStore::getPrsName, bo.getPrsName());
        lqw.eq(bo.getPrsDelete() != null, IcesProductStore::getPrsDelete, bo.getPrsDelete());
        return lqw;
    }

    /**
     * 新增产品仓库
     */
    @Override
    public Boolean insertByBo(IcesProductStoreBo bo) {
        IcesProductStore add = BeanUtil.toBean(bo, IcesProductStore.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPrsId(add.getPrsId());
        }
        return flag;
    }

    /**
     * 修改产品仓库
     */
    @Override
    public Boolean updateByBo(IcesProductStoreBo bo) {
        IcesProductStore update = BeanUtil.toBean(bo, IcesProductStore.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProductStore entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除产品仓库
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
