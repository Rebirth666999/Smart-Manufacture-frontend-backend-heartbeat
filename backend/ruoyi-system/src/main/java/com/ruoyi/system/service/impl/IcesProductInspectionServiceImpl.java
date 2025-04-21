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
import com.ruoyi.system.domain.bo.IcesProductInspectionBo;
import com.ruoyi.system.domain.vo.IcesProductInspectionVo;
import com.ruoyi.system.domain.IcesProductInspection;
import com.ruoyi.system.mapper.IcesProductInspectionMapper;
import com.ruoyi.system.service.IIcesProductInspectionService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 产品质检单Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@RequiredArgsConstructor
@Service
public class IcesProductInspectionServiceImpl implements IIcesProductInspectionService {

    private final IcesProductInspectionMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询产品质检单
     */
    @Override
    public IcesProductInspectionVo queryById(Long piId){
        return baseMapper.selectVoById(piId);
    }

    /**
     * 查询产品质检单列表
     */
    @Override
    public TableDataInfo<IcesProductInspectionVo> queryPageList(IcesProductInspectionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProductInspection> lqw = buildQueryWrapper(bo);
        Page<IcesProductInspectionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询产品质检单列表
     */
    @Override
    public List<IcesProductInspectionVo> queryList(IcesProductInspectionBo bo) {
        LambdaQueryWrapper<IcesProductInspection> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProductInspection> buildQueryWrapper(IcesProductInspectionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProductInspection> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPiCode()), IcesProductInspection::getPiCode, bo.getPiCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPbCode()), IcesProductInspection::getPbCode, bo.getPbCode());
        lqw.eq(bo.getPiDelete() != null, IcesProductInspection::getPiDelete, bo.getPiDelete());
        return lqw;
    }

    /**
     * 新增产品质检单
     */
    @Override
    public Boolean insertByBo(IcesProductInspectionBo bo) {
        bo.setPiCode(codeService.insertByType("ProductInspection"));
        // 自动填写负责人，部门，开始日期
        String[] cInfo = getLoginUsername();
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (cInfo != null) {
            bo.setPiMan(cInfo[0]);
            bo.setPiDept(cInfo[1]);
        }
        bo.setPiSdate(cDate);
        IcesProductInspection add = BeanUtil.toBean(bo, IcesProductInspection.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPiId(add.getPiId());
        }
        return flag;
    }

    /**
     * 修改产品质检单
     */
    @Override
    public Boolean updateByBo(IcesProductInspectionBo bo) {
        IcesProductInspection update = BeanUtil.toBean(bo, IcesProductInspection.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 获取当前用户信息
     * @return 包含用户名和部门名的数组
     */
    private String[] getLoginUsername() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            return null;
        }
        if (ObjectUtil.isNotNull(loginUser)) {
            String[] info = new String[2];
            info[0] = loginUser.getUsername();
            info[1] = loginUser.getDeptName();
            return info;
        } else {
            return null;
        }
    }


    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProductInspection entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除产品质检单
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
