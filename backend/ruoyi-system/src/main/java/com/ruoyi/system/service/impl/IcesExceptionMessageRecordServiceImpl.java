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
import com.ruoyi.system.domain.bo.IcesExceptionMessageRecordBo;
import com.ruoyi.system.domain.vo.IcesExceptionMessageRecordVo;
import com.ruoyi.system.domain.IcesExceptionMessageRecord;
import com.ruoyi.system.mapper.IcesExceptionMessageRecordMapper;
import com.ruoyi.system.service.IIcesExceptionMessageRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常消息记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionMessageRecordServiceImpl implements IIcesExceptionMessageRecordService {

    private final IcesExceptionMessageRecordMapper baseMapper;

    /**
     * 查询异常消息记录
     */
    @Override
    public IcesExceptionMessageRecordVo queryById(Long exmrId){
        return baseMapper.selectVoById(exmrId);
    }

    /**
     * 查询异常消息记录列表
     */
    @Override
    public TableDataInfo<IcesExceptionMessageRecordVo> queryPageList(IcesExceptionMessageRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionMessageRecord> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionMessageRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常消息记录列表
     */
    @Override
    public List<IcesExceptionMessageRecordVo> queryList(IcesExceptionMessageRecordBo bo) {
        LambdaQueryWrapper<IcesExceptionMessageRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionMessageRecord> buildQueryWrapper(IcesExceptionMessageRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionMessageRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExmrCode()), IcesExceptionMessageRecord::getExmrCode, bo.getExmrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExwCode()), IcesExceptionMessageRecord::getExwCode, bo.getExwCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExwrCode()), IcesExceptionMessageRecord::getExwrCode, bo.getExwrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExmrResult()), IcesExceptionMessageRecord::getExmrResult, bo.getExmrResult());
        lqw.eq(bo.getExmrDelete() != null, IcesExceptionMessageRecord::getExmrDelete, bo.getExmrDelete());
        return lqw;
    }

    /**
     * 新增异常消息记录
     */
    @Override
    public Boolean insertByBo(IcesExceptionMessageRecordBo bo) {
        IcesExceptionMessageRecord add = BeanUtil.toBean(bo, IcesExceptionMessageRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExmrId(add.getExmrId());
        }
        return flag;
    }

    /**
     * 修改异常消息记录
     */
    @Override
    public Boolean updateByBo(IcesExceptionMessageRecordBo bo) {
        IcesExceptionMessageRecord update = BeanUtil.toBean(bo, IcesExceptionMessageRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionMessageRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常消息记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
