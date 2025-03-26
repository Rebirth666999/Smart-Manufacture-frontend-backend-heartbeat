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
import com.ruoyi.system.domain.bo.IcesAreaBo;
import com.ruoyi.system.domain.vo.IcesAreaVo;
import com.ruoyi.system.domain.IcesArea;
import com.ruoyi.system.mapper.IcesAreaMapper;
import com.ruoyi.system.service.IIcesAreaService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 车间Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-09
 */
@RequiredArgsConstructor
@Service
public class IcesAreaServiceImpl implements IIcesAreaService {

    private final IcesAreaMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询车间
     */
    @Override
    public IcesAreaVo queryById(Long arId){
        return baseMapper.selectVoById(arId);
    }

    /**
     * 查询车间列表
     */
    @Override
    public TableDataInfo<IcesAreaVo> queryPageList(IcesAreaBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesArea> lqw = buildQueryWrapper(bo);
        Page<IcesAreaVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询车间列表
     */
    @Override
    public List<IcesAreaVo> queryList(IcesAreaBo bo) {
        LambdaQueryWrapper<IcesArea> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesArea> buildQueryWrapper(IcesAreaBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesArea> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getArName()), IcesArea::getArName, bo.getArName());
        lqw.eq(bo.getArDelete() != null, IcesArea::getArDelete, bo.getArDelete());
        return lqw;
    }

    /**
     * 新增车间
     */
    @Override
    public Boolean insertByBo(IcesAreaBo bo) {
        bo.setArCode(codeService.insertByType("Area"));
        IcesArea add = BeanUtil.toBean(bo, IcesArea.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setArId(add.getArId());
        }
        return flag;
    }

    /**
     * 修改车间
     */
    @Override
    public Boolean updateByBo(IcesAreaBo bo) {
        IcesArea update = BeanUtil.toBean(bo, IcesArea.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesArea entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除车间
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
