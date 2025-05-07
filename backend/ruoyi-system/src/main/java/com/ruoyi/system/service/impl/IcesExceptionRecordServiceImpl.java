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
import com.ruoyi.system.domain.bo.IcesExceptionRecordBo;
import com.ruoyi.system.domain.vo.IcesExceptionRecordVo;
import com.ruoyi.system.domain.IcesExceptionRecord;
import com.ruoyi.system.mapper.IcesExceptionRecordMapper;
import com.ruoyi.system.service.IIcesExceptionRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionRecordServiceImpl implements IIcesExceptionRecordService {

    private final IcesExceptionRecordMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询异常记录
     */
    @Override
    public IcesExceptionRecordVo queryById(Long exrId){
        return baseMapper.selectVoById(exrId);
    }

    /**
     * 查询异常记录列表
     */
    @Override
    public TableDataInfo<IcesExceptionRecordVo> queryPageList(IcesExceptionRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionRecord> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常记录列表
     */
    @Override
    public List<IcesExceptionRecordVo> queryList(IcesExceptionRecordBo bo) {
        LambdaQueryWrapper<IcesExceptionRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionRecord> buildQueryWrapper(IcesExceptionRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExrCode()), IcesExceptionRecord::getExrCode, bo.getExrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExsCode()), IcesExceptionRecord::getExsCode, bo.getExsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesExceptionRecord::getExCode, bo.getExCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExrStat()), IcesExceptionRecord::getExrStat, bo.getExrStat());
        lqw.eq(StringUtils.isNotBlank(bo.getExrLevel()), IcesExceptionRecord::getExrLevel, bo.getExrLevel());
        lqw.eq(bo.getExrDelete() != null, IcesExceptionRecord::getExrDelete, bo.getExrDelete());
        return lqw;
    }

    /**
     * 新增异常记录
     */
    @Override
    public IcesExceptionRecordVo insertByBo(IcesExceptionRecordBo bo) {
        bo.setExrCode(codeService.insertByType("ExceptionRecord"));
        IcesExceptionRecord add = BeanUtil.toBean(bo, IcesExceptionRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExrId(add.getExrId());
        }
        return queryById(add.getExrId());
    }

    /**
     * 修改异常记录
     */
    @Override
    public Boolean updateByBo(IcesExceptionRecordBo bo) {
        IcesExceptionRecord update = BeanUtil.toBean(bo, IcesExceptionRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
