package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesEquipmentModelBo;
import com.ruoyi.system.domain.vo.IcesEquipmentModelVo;
import com.ruoyi.system.domain.IcesEquipmentModel;
import com.ruoyi.system.mapper.IcesEquipmentModelMapper;
import com.ruoyi.system.service.IIcesEquipmentModelService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设备模型Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentModelServiceImpl implements IIcesEquipmentModelService {

    private final IcesEquipmentModelMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询设备模型
     */
    @Override
    public IcesEquipmentModelVo queryById(Long emId){
        return baseMapper.selectVoById(emId);
    }

    /**
     * 查询设备模型列表
     */
    @Override
    public TableDataInfo<IcesEquipmentModelVo> queryPageList(IcesEquipmentModelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentModel> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentModelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 审核端查询设备模型列表
     */
    @Override
    public TableDataInfo<IcesEquipmentModelVo> queryReviewList(IcesEquipmentModelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentModel> lqw = buildQueryWrapper(bo);
        List<String> stats = new ArrayList<>();
        stats.add("2");  // 待审核
        stats.add("3");  // 审核中
        stats.add("6");  // 待审核（弃用）
        stats.add("7");  // 审核中（弃用）
        lqw.in(IcesEquipmentModel::getEmStat, stats);
        Page<IcesEquipmentModelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备模型列表
     */
    @Override
    public List<IcesEquipmentModelVo> queryList(IcesEquipmentModelBo bo) {
        LambdaQueryWrapper<IcesEquipmentModel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentModel> buildQueryWrapper(IcesEquipmentModelBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentModel> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getEmCode()), IcesEquipmentModel::getEmCode, bo.getEmCode());
        lqw.eq(StringUtils.isNotBlank(bo.getEmtCode()), IcesEquipmentModel::getEmtCode, bo.getEmtCode());
        lqw.like(StringUtils.isNotBlank(bo.getEmName()), IcesEquipmentModel::getEmName, bo.getEmName());
        lqw.eq(StringUtils.isNotBlank(bo.getEmStat()), IcesEquipmentModel::getEmStat, bo.getEmStat());
        lqw.eq(bo.getEmDelete() != null, IcesEquipmentModel::getEmDelete, bo.getEmDelete());
        return lqw;
    }

    /**
     * 新增设备模型
     */
    @Override
    public Boolean insertByBo(IcesEquipmentModelBo bo) {
        bo.setEmCode(codeService.insertByType("EquipmentModel"));
        String cMan = getLoginUsername();
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 填入创建信息
        bo.setEmCman(cMan);
        bo.setEmCdate(cDate);
        IcesEquipmentModel add = BeanUtil.toBean(bo, IcesEquipmentModel.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEmId(add.getEmId());
        }
        return flag;
    }

    /**
     * 修改设备模型
     */
    @Override
    public Boolean updateByBo(IcesEquipmentModelBo bo) {
        String mMan = getLoginUsername();
        String mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 填入修改信息
        bo.setEmMman(mMan);
        bo.setEmMdate(mDate);
        IcesEquipmentModel update = BeanUtil.toBean(bo, IcesEquipmentModel.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 获取当前用户名称
     * @return 用户名
     */
    private String getLoginUsername() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            return null;
        }
        return ObjectUtil.isNotNull(loginUser) ? loginUser.getUsername() : null;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentModel entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备模型
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
