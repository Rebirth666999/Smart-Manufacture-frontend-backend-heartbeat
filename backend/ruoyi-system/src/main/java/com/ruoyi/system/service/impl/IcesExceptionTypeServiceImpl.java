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
import com.ruoyi.system.domain.bo.IcesExceptionTypeBo;
import com.ruoyi.system.domain.vo.IcesExceptionTypeVo;
import com.ruoyi.system.domain.IcesExceptionType;
import com.ruoyi.system.mapper.IcesExceptionTypeMapper;
import com.ruoyi.system.service.IIcesExceptionTypeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常类型Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionTypeServiceImpl implements IIcesExceptionTypeService {

    private final IcesExceptionTypeMapper baseMapper;

    /**
     * 查询异常类型
     */
    @Override
    public IcesExceptionTypeVo queryById(Long extId){
        return baseMapper.selectVoById(extId);
    }

    /**
     * 查询异常类型列表
     */
    @Override
    public TableDataInfo<IcesExceptionTypeVo> queryPageList(IcesExceptionTypeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionType> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionTypeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常类型列表
     */
    @Override
    public List<IcesExceptionTypeVo> queryList(IcesExceptionTypeBo bo) {
        LambdaQueryWrapper<IcesExceptionType> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionType> buildQueryWrapper(IcesExceptionTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionType> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExtCode()), IcesExceptionType::getExtCode, bo.getExtCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExtCodeParent()), IcesExceptionType::getExtCodeParent, bo.getExtCodeParent());
        lqw.like(StringUtils.isNotBlank(bo.getExtName()), IcesExceptionType::getExtName, bo.getExtName());
        lqw.eq(bo.getExtDelete() != null, IcesExceptionType::getExtDelete, bo.getExtDelete());
        return lqw;
    }

    /**
     * 新增异常类型
     */
    @Override
    public Boolean insertByBo(IcesExceptionTypeBo bo) {
        IcesExceptionType add = BeanUtil.toBean(bo, IcesExceptionType.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExtId(add.getExtId());
        }
        return flag;
    }

    /**
     * 修改异常类型
     */
    @Override
    public Boolean updateByBo(IcesExceptionTypeBo bo) {
        IcesExceptionType update = BeanUtil.toBean(bo, IcesExceptionType.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionType entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常类型
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
