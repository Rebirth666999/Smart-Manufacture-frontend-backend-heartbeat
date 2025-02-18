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
import com.ruoyi.system.domain.bo.IcesProductRecordBo;
import com.ruoyi.system.domain.vo.IcesProductRecordVo;
import com.ruoyi.system.domain.IcesProductRecord;
import com.ruoyi.system.mapper.IcesProductRecordMapper;
import com.ruoyi.system.service.IIcesProductRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 产品台账Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-18
 */
@RequiredArgsConstructor
@Service
public class IcesProductRecordServiceImpl implements IIcesProductRecordService {

    private final IcesProductRecordMapper baseMapper;

    /**
     * 查询产品台账
     */
    @Override
    public IcesProductRecordVo queryById(Long prrId){
        return baseMapper.selectVoById(prrId);
    }

    /**
     * 查询产品台账列表
     */
    @Override
    public TableDataInfo<IcesProductRecordVo> queryPageList(IcesProductRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProductRecord> lqw = buildQueryWrapper(bo);
        Page<IcesProductRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询产品台账列表
     */
    @Override
    public List<IcesProductRecordVo> queryList(IcesProductRecordBo bo) {
        LambdaQueryWrapper<IcesProductRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProductRecord> buildQueryWrapper(IcesProductRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProductRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPrId() != null, IcesProductRecord::getPrId, bo.getPrId());
        lqw.eq(StringUtils.isNotBlank(bo.getPrrType()), IcesProductRecord::getPrrType, bo.getPrrType());
        lqw.eq(bo.getPrrDelete() != null, IcesProductRecord::getPrrDelete, bo.getPrrDelete());
        lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
            IcesProductRecord::getCreateTime ,params.get("beginCreateTime"), params.get("endCreateTime"));
        return lqw;
    }

    /**
     * 新增产品台账
     */
    @Override
    public Boolean insertByBo(IcesProductRecordBo bo) {
        IcesProductRecord add = BeanUtil.toBean(bo, IcesProductRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPrrId(add.getPrrId());
        }
        return flag;
    }

    /**
     * 修改产品台账
     */
    @Override
    public Boolean updateByBo(IcesProductRecordBo bo) {
        IcesProductRecord update = BeanUtil.toBean(bo, IcesProductRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProductRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除产品台账
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
