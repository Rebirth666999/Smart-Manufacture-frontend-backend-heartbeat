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
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesClientBo;
import com.ruoyi.system.domain.vo.IcesClientVo;
import com.ruoyi.system.domain.IcesClient;
import com.ruoyi.system.mapper.IcesClientMapper;
import com.ruoyi.system.service.IIcesClientService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 客户基本信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@RequiredArgsConstructor
@Service
public class IcesClientServiceImpl implements IIcesClientService {

    private final IcesClientMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询客户基本信息
     */
    @Override
    public IcesClientVo queryById(Long clId){
        return baseMapper.selectVoById(clId);
    }

    /**
     * 查询客户基本信息列表
     */
    @Override
    public TableDataInfo<IcesClientVo> queryPageList(IcesClientBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesClient> lqw = buildQueryWrapper(bo);
        Page<IcesClientVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户基本信息列表
     */
    @Override
    public List<IcesClientVo> queryList(IcesClientBo bo) {
        LambdaQueryWrapper<IcesClient> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesClient> buildQueryWrapper(IcesClientBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesClient> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getClCode()), IcesClient::getClCode, bo.getClCode());
        lqw.eq(StringUtils.isNotBlank(bo.getCllCode()), IcesClient::getCllCode, bo.getCllCode());
        lqw.eq(bo.getClOperator() != null, IcesClient::getClOperator, bo.getClOperator());
        lqw.like(StringUtils.isNotBlank(bo.getClName()), IcesClient::getClName, bo.getClName());
        lqw.eq(StringUtils.isNotBlank(bo.getClStat()), IcesClient::getClStat, bo.getClStat());
        lqw.eq(bo.getClDelete() != null, IcesClient::getClDelete, bo.getClDelete());
        return lqw;
    }

    /**
     * 新增客户基本信息
     */
    @Override
    public IcesClientVo insertByBo(IcesClientBo bo) {
        bo.setClCode(codeService.insertByType("Client"));
        String cMan = getLoginUsername();
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 填入创建信息
        bo.setClCman(cMan);
        bo.setClCdate(cDate);
        if (bo.getClStat().equals("2")) {
            // 若已启用则填入发布信息
            bo.setClRman(cMan);
            bo.setClRdate(cDate);
        }
        IcesClient add = BeanUtil.toBean(bo, IcesClient.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setClId(add.getClId());
        }
        return queryById(add.getClId());
    }

    /**
     * 修改客户基本信息
     */
    @Override
    public Boolean updateByBo(IcesClientBo bo) {
        String mMan = getLoginUsername();
        String mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 获取原先记录
        IcesClientVo vo = queryById(bo.getClId());
        // 如果原先未启用，现在已启用，则填入发布信息
        if (vo.getClStat() != null && vo.getClStat().equals("1") && bo.getClStat().equals("2")) {
            bo.setClRman(mMan);
            bo.setClRdate(mDate);
        }
        // 填入修改信息
        bo.setClMman(mMan);
        bo.setClMdate(mDate);
        IcesClient update = BeanUtil.toBean(bo, IcesClient.class);
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
    private void validEntityBeforeSave(IcesClient entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户基本信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
