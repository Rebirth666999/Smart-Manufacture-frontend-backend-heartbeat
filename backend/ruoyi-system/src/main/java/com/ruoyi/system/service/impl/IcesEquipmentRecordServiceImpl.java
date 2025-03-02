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
import com.ruoyi.system.domain.bo.IcesEquipmentRecordBo;
import com.ruoyi.system.domain.vo.IcesEquipmentRecordVo;
import com.ruoyi.system.domain.IcesEquipmentRecord;
import com.ruoyi.system.mapper.IcesEquipmentRecordMapper;
import com.ruoyi.system.service.IIcesEquipmentRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备事件日志Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-01
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentRecordServiceImpl implements IIcesEquipmentRecordService {

    private final IcesEquipmentRecordMapper baseMapper;

    /**
     * 查询设备事件日志
     */
    @Override
    public IcesEquipmentRecordVo queryById(Long erId){
        return baseMapper.selectVoById(erId);
    }

    /**
     * 查询设备事件日志列表
     */
    @Override
    public TableDataInfo<IcesEquipmentRecordVo> queryPageList(IcesEquipmentRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentRecord> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备事件日志列表
     */
    @Override
    public List<IcesEquipmentRecordVo> queryList(IcesEquipmentRecordBo bo) {
        LambdaQueryWrapper<IcesEquipmentRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentRecord> buildQueryWrapper(IcesEquipmentRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getEqId() != null, IcesEquipmentRecord::getEqId, bo.getEqId());
        lqw.eq(StringUtils.isNotBlank(bo.getErType()), IcesEquipmentRecord::getErType, bo.getErType());
        lqw.eq(StringUtils.isNotBlank(bo.getErStat()), IcesEquipmentRecord::getErStat, bo.getErStat());
        lqw.between(params.get("beginErBegin") != null && params.get("endErBegin") != null,
            IcesEquipmentRecord::getErBegin ,params.get("beginErBegin"), params.get("endErBegin"));
        lqw.eq(bo.getErDelete() != null, IcesEquipmentRecord::getErDelete, bo.getErDelete());
        return lqw;
    }

    /**
     * 新增设备事件日志
     */
    @Override
    public Boolean insertByBo(IcesEquipmentRecordBo bo) {
        IcesEquipmentRecord add = BeanUtil.toBean(bo, IcesEquipmentRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setErId(add.getErId());
        }
        return flag;
    }

    /**
     * 修改设备事件日志
     */
    @Override
    public Boolean updateByBo(IcesEquipmentRecordBo bo) {
        IcesEquipmentRecord update = BeanUtil.toBean(bo, IcesEquipmentRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备事件日志
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
