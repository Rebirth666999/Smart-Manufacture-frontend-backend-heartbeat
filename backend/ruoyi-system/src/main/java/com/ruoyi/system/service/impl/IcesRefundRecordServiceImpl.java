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
import com.ruoyi.system.domain.bo.IcesRefundRecordBo;
import com.ruoyi.system.domain.vo.IcesRefundRecordVo;
import com.ruoyi.system.domain.IcesRefundRecord;
import com.ruoyi.system.mapper.IcesRefundRecordMapper;
import com.ruoyi.system.service.IIcesRefundRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 退货记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@RequiredArgsConstructor
@Service
public class IcesRefundRecordServiceImpl implements IIcesRefundRecordService {

    private final IcesRefundRecordMapper baseMapper;

    /**
     * 查询退货记录
     */
    @Override
    public IcesRefundRecordVo queryById(Long rrId){
        return baseMapper.selectVoById(rrId);
    }

    /**
     * 查询退货记录列表
     */
    @Override
    public TableDataInfo<IcesRefundRecordVo> queryPageList(IcesRefundRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesRefundRecord> lqw = buildQueryWrapper(bo);
        Page<IcesRefundRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询退货记录列表
     */
    @Override
    public List<IcesRefundRecordVo> queryList(IcesRefundRecordBo bo) {
        LambdaQueryWrapper<IcesRefundRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesRefundRecord> buildQueryWrapper(IcesRefundRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesRefundRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getRrCode()), IcesRefundRecord::getRrCode, bo.getRrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getOrCode()), IcesRefundRecord::getOrCode, bo.getOrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrCode()), IcesRefundRecord::getPrCode, bo.getPrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getClCode()), IcesRefundRecord::getClCode, bo.getClCode());
        lqw.eq(bo.getRrDelete() != null, IcesRefundRecord::getRrDelete, bo.getRrDelete());
        lqw.eq(StringUtils.isNotBlank(bo.getRrMan()), IcesRefundRecord::getRrMan, bo.getRrMan());
        lqw.eq(StringUtils.isNotBlank(bo.getRrDate()), IcesRefundRecord::getRrDate, bo.getRrDate());
        return lqw;
    }

    /**
     * 新增退货记录
     */
    @Override
    public Boolean insertByBo(IcesRefundRecordBo bo) {
        IcesRefundRecord add = BeanUtil.toBean(bo, IcesRefundRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRrId(add.getRrId());
        }
        return flag;
    }

    /**
     * 修改退货记录
     */
    @Override
    public Boolean updateByBo(IcesRefundRecordBo bo) {
        IcesRefundRecord update = BeanUtil.toBean(bo, IcesRefundRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesRefundRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除退货记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
