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
import com.ruoyi.system.domain.bo.IcesMaterialBo;
import com.ruoyi.system.domain.vo.IcesMaterialVo;
import com.ruoyi.system.domain.IcesMaterial;
import com.ruoyi.system.mapper.IcesMaterialMapper;
import com.ruoyi.system.service.IIcesMaterialService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 物料Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@RequiredArgsConstructor
@Service
public class IcesMaterialServiceImpl implements IIcesMaterialService {

    private final IcesMaterialMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询物料
     */
    @Override
    public IcesMaterialVo queryById(Long maId){
        return baseMapper.selectVoById(maId);
    }

    /**
     * 查询物料列表
     */
    @Override
    public TableDataInfo<IcesMaterialVo> queryPageList(IcesMaterialBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesMaterial> lqw = buildQueryWrapper(bo);
        Page<IcesMaterialVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询物料列表
     */
    @Override
    public List<IcesMaterialVo> queryList(IcesMaterialBo bo) {
        LambdaQueryWrapper<IcesMaterial> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesMaterial> buildQueryWrapper(IcesMaterialBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesMaterial> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMaCode()), IcesMaterial::getMaCode, bo.getMaCode());
        lqw.like(StringUtils.isNotBlank(bo.getMaName()), IcesMaterial::getMaName, bo.getMaName());
        lqw.eq(StringUtils.isNotBlank(bo.getMaType()), IcesMaterial::getMaType, bo.getMaType());
        lqw.eq(bo.getMaDelete() != null, IcesMaterial::getMaDelete, bo.getMaDelete());
        return lqw;
    }

    /**
     * 新增物料
     */
    @Override
    public Boolean insertByBo(IcesMaterialBo bo) {
        bo.setMaCode(codeService.insertByType("Material"));
        IcesMaterial add = BeanUtil.toBean(bo, IcesMaterial.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMaId(add.getMaId());
        }
        return flag;
    }

    /**
     * 修改物料
     */
    @Override
    public Boolean updateByBo(IcesMaterialBo bo) {
        IcesMaterial update = BeanUtil.toBean(bo, IcesMaterial.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesMaterial entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除物料
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
