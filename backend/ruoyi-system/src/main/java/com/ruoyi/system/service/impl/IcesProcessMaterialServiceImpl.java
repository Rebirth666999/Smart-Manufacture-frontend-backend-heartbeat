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
import com.ruoyi.system.domain.bo.IcesProcessMaterialBo;
import com.ruoyi.system.domain.vo.IcesProcessMaterialVo;
import com.ruoyi.system.domain.IcesProcessMaterial;
import com.ruoyi.system.mapper.IcesProcessMaterialMapper;
import com.ruoyi.system.service.IIcesProcessMaterialService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 关联-工艺流程原料需求Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-12
 */
@RequiredArgsConstructor
@Service
public class IcesProcessMaterialServiceImpl implements IIcesProcessMaterialService {

    private final IcesProcessMaterialMapper baseMapper;

    /**
     * 查询关联-工艺流程原料需求
     */
    @Override
    public IcesProcessMaterialVo queryById(Long pmId){
        return baseMapper.selectVoById(pmId);
    }

    /**
     * 查询关联-工艺流程原料需求列表
     */
    @Override
    public TableDataInfo<IcesProcessMaterialVo> queryPageList(IcesProcessMaterialBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProcessMaterial> lqw = buildQueryWrapper(bo);
        Page<IcesProcessMaterialVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询关联-工艺流程原料需求列表
     */
    @Override
    public List<IcesProcessMaterialVo> queryList(IcesProcessMaterialBo bo) {
        LambdaQueryWrapper<IcesProcessMaterial> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProcessMaterial> buildQueryWrapper(IcesProcessMaterialBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProcessMaterial> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProcId() != null, IcesProcessMaterial::getProcId, bo.getProcId());
        lqw.eq(bo.getMaId() != null, IcesProcessMaterial::getMaId, bo.getMaId());
        lqw.eq(bo.getPmDelete() != null, IcesProcessMaterial::getPmDelete, bo.getPmDelete());
        return lqw;
    }

    /**
     * 新增关联-工艺流程原料需求
     */
    @Override
    public Boolean insertByBo(IcesProcessMaterialBo bo) {
        IcesProcessMaterial add = BeanUtil.toBean(bo, IcesProcessMaterial.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPmId(add.getPmId());
        }
        return flag;
    }

    /**
     * 修改关联-工艺流程原料需求
     */
    @Override
    public Boolean updateByBo(IcesProcessMaterialBo bo) {
        IcesProcessMaterial update = BeanUtil.toBean(bo, IcesProcessMaterial.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProcessMaterial entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除关联-工艺流程原料需求
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
