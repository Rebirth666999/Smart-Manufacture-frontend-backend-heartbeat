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
import com.ruoyi.system.domain.bo.IcesProductInspectionDetailBo;
import com.ruoyi.system.domain.vo.IcesProductInspectionDetailVo;
import com.ruoyi.system.domain.IcesProductInspectionDetail;
import com.ruoyi.system.mapper.IcesProductInspectionDetailMapper;
import com.ruoyi.system.service.IIcesProductInspectionDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 产品质检单明细Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@RequiredArgsConstructor
@Service
public class IcesProductInspectionDetailServiceImpl implements IIcesProductInspectionDetailService {

    private final IcesProductInspectionDetailMapper baseMapper;

    /**
     * 查询产品质检单明细
     */
    @Override
    public IcesProductInspectionDetailVo queryById(Long pidId){
        return baseMapper.selectVoById(pidId);
    }

    /**
     * 查询产品质检单明细列表
     */
    @Override
    public TableDataInfo<IcesProductInspectionDetailVo> queryPageList(IcesProductInspectionDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProductInspectionDetail> lqw = buildQueryWrapper(bo);
        Page<IcesProductInspectionDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询产品质检单明细列表
     */
    @Override
    public List<IcesProductInspectionDetailVo> queryList(IcesProductInspectionDetailBo bo) {
        LambdaQueryWrapper<IcesProductInspectionDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProductInspectionDetail> buildQueryWrapper(IcesProductInspectionDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProductInspectionDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPidCode()), IcesProductInspectionDetail::getPidCode, bo.getPidCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPiCode()), IcesProductInspectionDetail::getPiCode, bo.getPiCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrCode()), IcesProductInspectionDetail::getPrCode, bo.getPrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPidFlag()), IcesProductInspectionDetail::getPidFlag, bo.getPidFlag());
        lqw.eq(bo.getPidDelete() != null, IcesProductInspectionDetail::getPidDelete, bo.getPidDelete());
        return lqw;
    }

    /**
     * 新增产品质检单明细
     */
    @Override
    public Boolean insertByBo(IcesProductInspectionDetailBo bo) {
        IcesProductInspectionDetail add = BeanUtil.toBean(bo, IcesProductInspectionDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPidId(add.getPidId());
        }
        return flag;
    }

    /**
     * 修改产品质检单明细
     */
    @Override
    public Boolean updateByBo(IcesProductInspectionDetailBo bo) {
        IcesProductInspectionDetail update = BeanUtil.toBean(bo, IcesProductInspectionDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProductInspectionDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除产品质检单明细
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
