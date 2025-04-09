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
import com.ruoyi.system.domain.bo.IcesExceptionRecordLogBo;
import com.ruoyi.system.domain.vo.IcesExceptionRecordLogVo;
import com.ruoyi.system.domain.IcesExceptionRecordLog;
import com.ruoyi.system.mapper.IcesExceptionRecordLogMapper;
import com.ruoyi.system.service.IIcesExceptionRecordLogService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常处理日志Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionRecordLogServiceImpl implements IIcesExceptionRecordLogService {

    private final IcesExceptionRecordLogMapper baseMapper;

    /**
     * 查询异常处理日志
     */
    @Override
    public IcesExceptionRecordLogVo queryById(Long exrlId){
        return baseMapper.selectVoById(exrlId);
    }

    /**
     * 查询异常处理日志列表
     */
    @Override
    public TableDataInfo<IcesExceptionRecordLogVo> queryPageList(IcesExceptionRecordLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionRecordLog> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionRecordLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常处理日志列表
     */
    @Override
    public List<IcesExceptionRecordLogVo> queryList(IcesExceptionRecordLogBo bo) {
        LambdaQueryWrapper<IcesExceptionRecordLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionRecordLog> buildQueryWrapper(IcesExceptionRecordLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionRecordLog> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExrlCode()), IcesExceptionRecordLog::getExrlCode, bo.getExrlCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExrCode()), IcesExceptionRecordLog::getExrCode, bo.getExrCode());
        lqw.eq(bo.getExrlDelete() != null, IcesExceptionRecordLog::getExrlDelete, bo.getExrlDelete());
        return lqw;
    }

    /**
     * 新增异常处理日志
     */
    @Override
    public Boolean insertByBo(IcesExceptionRecordLogBo bo) {
        IcesExceptionRecordLog add = BeanUtil.toBean(bo, IcesExceptionRecordLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExrlId(add.getExrlId());
        }
        return flag;
    }

    /**
     * 修改异常处理日志
     */
    @Override
    public Boolean updateByBo(IcesExceptionRecordLogBo bo) {
        IcesExceptionRecordLog update = BeanUtil.toBean(bo, IcesExceptionRecordLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionRecordLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常处理日志
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
