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
import com.ruoyi.system.domain.bo.IcesExceptionWarningBo;
import com.ruoyi.system.domain.vo.IcesExceptionWarningVo;
import com.ruoyi.system.domain.IcesExceptionWarning;
import com.ruoyi.system.mapper.IcesExceptionWarningMapper;
import com.ruoyi.system.service.IIcesExceptionWarningService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常预警配置Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionWarningServiceImpl implements IIcesExceptionWarningService {

    private final IcesExceptionWarningMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询异常预警配置
     */
    @Override
    public IcesExceptionWarningVo queryById(Long exwId){
        return baseMapper.selectVoById(exwId);
    }

    /**
     * 查询异常预警配置列表
     */
    @Override
    public TableDataInfo<IcesExceptionWarningVo> queryPageList(IcesExceptionWarningBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionWarning> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionWarningVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常预警配置列表
     */
    @Override
    public List<IcesExceptionWarningVo> queryList(IcesExceptionWarningBo bo) {
        LambdaQueryWrapper<IcesExceptionWarning> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionWarning> buildQueryWrapper(IcesExceptionWarningBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionWarning> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExwCode()), IcesExceptionWarning::getExwCode, bo.getExwCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesExceptionWarning::getExCode, bo.getExCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExsCode()), IcesExceptionWarning::getExsCode, bo.getExsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExwWarningOrgn()), IcesExceptionWarning::getExwWarningOrgn, bo.getExwWarningOrgn());
        lqw.eq(StringUtils.isNotBlank(bo.getExwHandleOrgn()), IcesExceptionWarning::getExwHandleOrgn, bo.getExwHandleOrgn());
        lqw.eq(bo.getExwDelete() != null, IcesExceptionWarning::getExwDelete, bo.getExwDelete());
        return lqw;
    }

    /**
     * 新增异常预警配置
     */
    @Override
    public Boolean insertByBo(IcesExceptionWarningBo bo) {
        bo.setExwCode(codeService.insertByType("ExceptionWarning"));
        IcesExceptionWarning add = BeanUtil.toBean(bo, IcesExceptionWarning.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExwId(add.getExwId());
        }
        return flag;
    }

    /**
     * 修改异常预警配置
     */
    @Override
    public Boolean updateByBo(IcesExceptionWarningBo bo) {
        IcesExceptionWarning update = BeanUtil.toBean(bo, IcesExceptionWarning.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionWarning entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常预警配置
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
