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
import com.ruoyi.system.domain.bo.IcesCodeBo;
import com.ruoyi.system.domain.vo.IcesCodeVo;
import com.ruoyi.system.domain.IcesCode;
import com.ruoyi.system.mapper.IcesCodeMapper;
import com.ruoyi.system.service.IIcesCodeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 业务编码Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@RequiredArgsConstructor
@Service
public class IcesCodeServiceImpl implements IIcesCodeService {

    private final IcesCodeMapper baseMapper;

    /**
     * 查询业务编码
     */
    @Override
    public IcesCodeVo queryById(Long coId){
        return baseMapper.selectVoById(coId);
    }

    /**
     * 查询业务编码列表
     */
    @Override
    public TableDataInfo<IcesCodeVo> queryPageList(IcesCodeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesCode> lqw = buildQueryWrapper(bo);
        Page<IcesCodeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询业务编码列表
     */
    @Override
    public List<IcesCodeVo> queryList(IcesCodeBo bo) {
        LambdaQueryWrapper<IcesCode> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesCode> buildQueryWrapper(IcesCodeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesCode> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getCoType()), IcesCode::getCoType, bo.getCoType());
        lqw.eq(bo.getCoNum() != null, IcesCode::getCoNum, bo.getCoNum());
        lqw.eq(bo.getCoDelete() != null, IcesCode::getCoDelete, bo.getCoDelete());
        return lqw;
    }

    /**
     * 新增业务编码
     */
    @Override
    public Boolean insertByBo(IcesCodeBo bo) {
        IcesCode add = BeanUtil.toBean(bo, IcesCode.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCoId(add.getCoId());
        }
        return flag;
    }

    /**
     * 修改业务编码
     */
    @Override
    public Boolean updateByBo(IcesCodeBo bo) {
        IcesCode update = BeanUtil.toBean(bo, IcesCode.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesCode entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除业务编码
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
