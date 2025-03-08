package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.IcesEquipmentModelBo;
import com.ruoyi.system.domain.vo.IcesEquipmentModelVo;
import com.ruoyi.system.service.IIcesEquipmentModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesEquipmentModelTypeBo;
import com.ruoyi.system.domain.vo.IcesEquipmentModelTypeVo;
import com.ruoyi.system.domain.IcesEquipmentModelType;
import com.ruoyi.system.mapper.IcesEquipmentModelTypeMapper;
import com.ruoyi.system.service.IIcesEquipmentModelTypeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备模型类型Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-18
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentModelTypeServiceImpl implements IIcesEquipmentModelTypeService {

    private final IcesEquipmentModelTypeMapper baseMapper;
    private final IIcesEquipmentModelService iIcesEquipmentModelService;

    /**
     * 查询设备模型类型
     */
    @Override
    public IcesEquipmentModelTypeVo queryById(Long emtId){
        return baseMapper.selectVoById(emtId);
    }

    /**
     * 查询设备模型类型列表
     */
    @Override
    public TableDataInfo<IcesEquipmentModelTypeVo> queryPageList(IcesEquipmentModelTypeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentModelType> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentModelTypeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备模型类型列表
     */
    @Override
    public List<IcesEquipmentModelTypeVo> queryList(IcesEquipmentModelTypeBo bo) {
        LambdaQueryWrapper<IcesEquipmentModelType> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentModelType> buildQueryWrapper(IcesEquipmentModelTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentModelType> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getEmtName()), IcesEquipmentModelType::getEmtName, bo.getEmtName());
        lqw.eq(bo.getEmtDelete() != null, IcesEquipmentModelType::getEmtDelete, bo.getEmtDelete());
        return lqw;
    }

    /**
     * 新增设备模型类型
     */
    @Override
    public Boolean insertByBo(IcesEquipmentModelTypeBo bo) {
        IcesEquipmentModelType add = BeanUtil.toBean(bo, IcesEquipmentModelType.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEmtId(add.getEmtId());
        }
        return flag;
    }

    /**
     * 修改设备模型类型
     */
    @Override
    public Boolean updateByBo(IcesEquipmentModelTypeBo bo) {
        IcesEquipmentModelType update = BeanUtil.toBean(bo, IcesEquipmentModelType.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentModelType entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备模型类型
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        // 设备模型类型删除逻辑
        // 类型下没有模型，直接从数据库删除
        // 类型下只有已弃用的模型，更改已删除字段
        // 不满足如上情况，拒绝删除
        if(isValid){
            for (Long id : ids) {
                // 构建搜索条件
                IcesEquipmentModelBo equipmentModelBo = new IcesEquipmentModelBo();
                equipmentModelBo.setEmtId(id);
                // 找到所有设备模型
                List<IcesEquipmentModelVo> list = iIcesEquipmentModelService.queryList(equipmentModelBo);
                for (IcesEquipmentModelVo vo : list) {
                    // 设备模型不是已弃用，拒绝删除
                    if (!("5".equals(vo.getEmStat()))) {
                        return false;
                    }
                }
            }
        }
        for (Long id : ids) {
            IcesEquipmentModelBo equipmentModelBo = new IcesEquipmentModelBo();
            equipmentModelBo.setEmtId(id);
            List<IcesEquipmentModelVo> list = iIcesEquipmentModelService.queryList(equipmentModelBo);
            if (list.isEmpty()) {
                baseMapper.deleteById(id);
            } else {
                IcesEquipmentModelTypeBo modelTypeBo = new IcesEquipmentModelTypeBo();
                modelTypeBo.setEmtId(id);
                modelTypeBo.setEmtDelete(1L);
                updateByBo(modelTypeBo);
            }
        }
        return true;
    }
}
