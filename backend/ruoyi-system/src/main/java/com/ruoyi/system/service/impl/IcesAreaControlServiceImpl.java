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
import com.ruoyi.system.domain.bo.IcesAreaControlBo;
import com.ruoyi.system.domain.vo.IcesAreaControlVo;
import com.ruoyi.system.domain.IcesAreaControl;
import com.ruoyi.system.mapper.IcesAreaControlMapper;
import com.ruoyi.system.service.IIcesAreaControlService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 主控节点Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-19
 */
@RequiredArgsConstructor
@Service
public class IcesAreaControlServiceImpl implements IIcesAreaControlService {

    private final IcesAreaControlMapper baseMapper;

    /**
     * 查询主控节点
     */
    @Override
    public IcesAreaControlVo queryById(Long acId){
        return baseMapper.selectVoById(acId);
    }

    /**
     * 查询主控节点列表
     */
    @Override
    public TableDataInfo<IcesAreaControlVo> queryPageList(IcesAreaControlBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesAreaControl> lqw = buildQueryWrapper(bo);
        Page<IcesAreaControlVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询主控节点列表
     */
    @Override
    public List<IcesAreaControlVo> queryList(IcesAreaControlBo bo) {
        LambdaQueryWrapper<IcesAreaControl> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesAreaControl> buildQueryWrapper(IcesAreaControlBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesAreaControl> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getArId() != null, IcesAreaControl::getArId, bo.getArId());
        lqw.like(StringUtils.isNotBlank(bo.getAcName()), IcesAreaControl::getAcName, bo.getAcName());
        lqw.eq(StringUtils.isNotBlank(bo.getAcIp()), IcesAreaControl::getAcIp, bo.getAcIp());
        lqw.eq(bo.getAcDelete() != null, IcesAreaControl::getAcDelete, bo.getAcDelete());
        return lqw;
    }

    /**
     * 新增主控节点
     */
    @Override
    public Boolean insertByBo(IcesAreaControlBo bo) {
        IcesAreaControl add = BeanUtil.toBean(bo, IcesAreaControl.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAcId(add.getAcId());
        }
        return flag;
    }

    /**
     * 修改主控节点
     */
    @Override
    public Boolean updateByBo(IcesAreaControlBo bo) {
        IcesAreaControl update = BeanUtil.toBean(bo, IcesAreaControl.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesAreaControl entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除主控节点
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
