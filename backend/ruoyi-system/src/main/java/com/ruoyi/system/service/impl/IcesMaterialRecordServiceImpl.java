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
import com.ruoyi.system.domain.bo.IcesMaterialRecordBo;
import com.ruoyi.system.domain.vo.IcesMaterialRecordVo;
import com.ruoyi.system.domain.IcesMaterialRecord;
import com.ruoyi.system.mapper.IcesMaterialRecordMapper;
import com.ruoyi.system.service.IIcesMaterialRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 原料台账Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@RequiredArgsConstructor
@Service
public class IcesMaterialRecordServiceImpl implements IIcesMaterialRecordService {

    private final IcesMaterialRecordMapper baseMapper;

    /**
     * 查询原料台账
     */
    @Override
    public IcesMaterialRecordVo queryById(Long mrId){
        return baseMapper.selectVoById(mrId);
    }

    /**
     * 查询原料台账列表
     */
    @Override
    public TableDataInfo<IcesMaterialRecordVo> queryPageList(IcesMaterialRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesMaterialRecord> lqw = buildQueryWrapper(bo);
        Page<IcesMaterialRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询原料台账列表
     */
    @Override
    public List<IcesMaterialRecordVo> queryList(IcesMaterialRecordBo bo) {
        LambdaQueryWrapper<IcesMaterialRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesMaterialRecord> buildQueryWrapper(IcesMaterialRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesMaterialRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMaId() != null, IcesMaterialRecord::getMaId, bo.getMaId());
        lqw.eq(bo.getArId() != null, IcesMaterialRecord::getArId, bo.getArId());
        lqw.eq(StringUtils.isNotBlank(bo.getMrType()), IcesMaterialRecord::getMrType, bo.getMrType());
        lqw.eq(bo.getMrDelete() != null, IcesMaterialRecord::getMrDelete, bo.getMrDelete());
        lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
            IcesMaterialRecord::getCreateTime ,params.get("beginCreateTime"), params.get("endCreateTime"));
        return lqw;
    }

    /**
     * 新增原料台账
     */
    @Override
    public Boolean insertByBo(IcesMaterialRecordBo bo) {
        IcesMaterialRecord add = BeanUtil.toBean(bo, IcesMaterialRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMrId(add.getMrId());
        }
        return flag;
    }

    /**
     * 修改原料台账
     */
    @Override
    public Boolean updateByBo(IcesMaterialRecordBo bo) {
        IcesMaterialRecord update = BeanUtil.toBean(bo, IcesMaterialRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesMaterialRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除原料台账
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
