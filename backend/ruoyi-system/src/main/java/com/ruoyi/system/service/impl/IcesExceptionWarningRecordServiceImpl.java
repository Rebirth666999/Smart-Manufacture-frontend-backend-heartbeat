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
import com.ruoyi.system.domain.bo.IcesExceptionWarningRecordBo;
import com.ruoyi.system.domain.vo.IcesExceptionWarningRecordVo;
import com.ruoyi.system.domain.IcesExceptionWarningRecord;
import com.ruoyi.system.mapper.IcesExceptionWarningRecordMapper;
import com.ruoyi.system.service.IIcesExceptionWarningRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常预警记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionWarningRecordServiceImpl implements IIcesExceptionWarningRecordService {

    private final IcesExceptionWarningRecordMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询异常预警记录
     */
    @Override
    public IcesExceptionWarningRecordVo queryById(Long exwrId){
        return baseMapper.selectVoById(exwrId);
    }

    /**
     * 查询异常预警记录列表
     */
    @Override
    public TableDataInfo<IcesExceptionWarningRecordVo> queryPageList(IcesExceptionWarningRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionWarningRecord> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionWarningRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常预警记录列表
     */
    @Override
    public List<IcesExceptionWarningRecordVo> queryList(IcesExceptionWarningRecordBo bo) {
        LambdaQueryWrapper<IcesExceptionWarningRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionWarningRecord> buildQueryWrapper(IcesExceptionWarningRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionWarningRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExwrCode()), IcesExceptionWarningRecord::getExwrCode, bo.getExwrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExwCode()), IcesExceptionWarningRecord::getExwCode, bo.getExwCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExwrWarning()), IcesExceptionWarningRecord::getExwrWarning, bo.getExwrWarning());
        lqw.eq(bo.getExwrDelete() != null, IcesExceptionWarningRecord::getExwrDelete, bo.getExwrDelete());
        return lqw;
    }

    /**
     * 新增异常预警记录
     */
    @Override
    public Boolean insertByBo(IcesExceptionWarningRecordBo bo) {
        bo.setExwrCode(codeService.insertByType("ExceptionWarningRecord"));
        IcesExceptionWarningRecord add = BeanUtil.toBean(bo, IcesExceptionWarningRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExwrId(add.getExwrId());
        }
        return flag;
    }

    /**
     * 修改异常预警记录
     */
    @Override
    public Boolean updateByBo(IcesExceptionWarningRecordBo bo) {
        IcesExceptionWarningRecord update = BeanUtil.toBean(bo, IcesExceptionWarningRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionWarningRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常预警记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
