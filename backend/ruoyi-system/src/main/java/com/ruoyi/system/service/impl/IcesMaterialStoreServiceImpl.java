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
import com.ruoyi.system.domain.bo.IcesMaterialStoreBo;
import com.ruoyi.system.domain.vo.IcesMaterialStoreVo;
import com.ruoyi.system.domain.IcesMaterialStore;
import com.ruoyi.system.mapper.IcesMaterialStoreMapper;
import com.ruoyi.system.service.IIcesMaterialStoreService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 原料仓库Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@RequiredArgsConstructor
@Service
public class IcesMaterialStoreServiceImpl implements IIcesMaterialStoreService {

    private final IcesMaterialStoreMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询原料仓库
     */
    @Override
    public IcesMaterialStoreVo queryById(Long msId){
        return baseMapper.selectVoById(msId);
    }

    /**
     * 查询原料仓库列表
     */
    @Override
    public TableDataInfo<IcesMaterialStoreVo> queryPageList(IcesMaterialStoreBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesMaterialStore> lqw = buildQueryWrapper(bo);
        Page<IcesMaterialStoreVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询原料仓库列表
     */
    @Override
    public List<IcesMaterialStoreVo> queryList(IcesMaterialStoreBo bo) {
        LambdaQueryWrapper<IcesMaterialStore> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesMaterialStore> buildQueryWrapper(IcesMaterialStoreBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesMaterialStore> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMsCode()), IcesMaterialStore::getMsCode, bo.getMsCode());
        lqw.like(StringUtils.isNotBlank(bo.getMsName()), IcesMaterialStore::getMsName, bo.getMsName());
        lqw.eq(bo.getMsDelete() != null, IcesMaterialStore::getMsDelete, bo.getMsDelete());
        return lqw;
    }

    /**
     * 新增原料仓库
     */
    @Override
    public Boolean insertByBo(IcesMaterialStoreBo bo) {
        bo.setMsCode(codeService.insertByType("MaterialStore"));
        IcesMaterialStore add = BeanUtil.toBean(bo, IcesMaterialStore.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMsId(add.getMsId());
        }
        return flag;
    }

    /**
     * 修改原料仓库
     */
    @Override
    public Boolean updateByBo(IcesMaterialStoreBo bo) {
        IcesMaterialStore update = BeanUtil.toBean(bo, IcesMaterialStore.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesMaterialStore entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除原料仓库
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
