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
import com.ruoyi.system.domain.bo.IcesStoreBo;
import com.ruoyi.system.domain.vo.IcesStoreVo;
import com.ruoyi.system.domain.IcesStore;
import com.ruoyi.system.mapper.IcesStoreMapper;
import com.ruoyi.system.service.IIcesStoreService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 仓库Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@RequiredArgsConstructor
@Service
public class IcesStoreServiceImpl implements IIcesStoreService {

    private final IcesStoreMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询仓库
     */
    @Override
    public IcesStoreVo queryById(Long stId){
        return baseMapper.selectVoById(stId);
    }

    /**
     * 查询仓库列表
     */
    @Override
    public TableDataInfo<IcesStoreVo> queryPageList(IcesStoreBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesStore> lqw = buildQueryWrapper(bo);
        Page<IcesStoreVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询仓库列表
     */
    @Override
    public List<IcesStoreVo> queryList(IcesStoreBo bo) {
        LambdaQueryWrapper<IcesStore> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesStore> buildQueryWrapper(IcesStoreBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesStore> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getStCode()), IcesStore::getStCode, bo.getStCode());
        lqw.like(StringUtils.isNotBlank(bo.getStName()), IcesStore::getStName, bo.getStName());
        lqw.eq(StringUtils.isNotBlank(bo.getStType()), IcesStore::getStType, bo.getStType());
        lqw.eq(bo.getStDelete() != null, IcesStore::getStDelete, bo.getStDelete());
        return lqw;
    }

    /**
     * 新增仓库
     */
    @Override
    public Boolean insertByBo(IcesStoreBo bo) {
        bo.setStCode(codeService.insertByType("Store"));
        IcesStore add = BeanUtil.toBean(bo, IcesStore.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setStId(add.getStId());
        }
        return flag;
    }

    /**
     * 修改仓库
     */
    @Override
    public Boolean updateByBo(IcesStoreBo bo) {
        IcesStore update = BeanUtil.toBean(bo, IcesStore.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesStore entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除仓库
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
