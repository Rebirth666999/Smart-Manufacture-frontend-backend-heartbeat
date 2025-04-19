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
import com.ruoyi.system.domain.bo.IcesClientBo;
import com.ruoyi.system.domain.vo.IcesClientVo;
import com.ruoyi.system.service.IIcesClientService;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesClientTradeBo;
import com.ruoyi.system.domain.vo.IcesClientTradeVo;
import com.ruoyi.system.domain.IcesClientTrade;
import com.ruoyi.system.mapper.IcesClientTradeMapper;
import com.ruoyi.system.service.IIcesClientTradeService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 客户贸易信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@RequiredArgsConstructor
@Service
public class IcesClientTradeServiceImpl implements IIcesClientTradeService {

    private final IcesClientTradeMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesClientService clientService;

    /**
     * 查询客户贸易信息
     */
    @Override
    public IcesClientTradeVo queryById(Long ctId){
        return baseMapper.selectVoById(ctId);
    }

    /**
     * 查询客户贸易信息列表
     */
    @Override
    public TableDataInfo<IcesClientTradeVo> queryPageList(IcesClientTradeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesClientTrade> lqw = buildQueryWrapper(bo);
        Page<IcesClientTradeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户贸易信息列表
     */
    @Override
    public List<IcesClientTradeVo> queryList(IcesClientTradeBo bo) {
        LambdaQueryWrapper<IcesClientTrade> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesClientTrade> buildQueryWrapper(IcesClientTradeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesClientTrade> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getCtCode()), IcesClientTrade::getCtCode, bo.getCtCode());
        lqw.eq(StringUtils.isNotBlank(bo.getClCode()), IcesClientTrade::getClCode, bo.getClCode());
        lqw.eq(bo.getCtDelete() != null, IcesClientTrade::getCtDelete, bo.getCtDelete());
        return lqw;
    }

    /**
     * 新增客户贸易信息
     */
    @Override
    public Boolean insertByBo(IcesClientTradeBo bo) {
        bo.setCtCode(codeService.insertByType("ClientTrade"));
        IcesClientTrade add = BeanUtil.toBean(bo, IcesClientTrade.class);
        validEntityBeforeSave(add);
        updateClient(bo);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCtId(add.getCtId());
        }
        return flag;
    }

    /**
     * 修改客户贸易信息
     */
    @Override
    public Boolean updateByBo(IcesClientTradeBo bo) {
        IcesClientTrade update = BeanUtil.toBean(bo, IcesClientTrade.class);
        validEntityBeforeSave(update);
        updateClient(bo);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 新增/修改贸易信息算作对客户信息的修改
     * 需要更新客户信息的修改人、修改时间字段
     */
    private void updateClient(IcesClientTradeBo bo) {
        // 搜索条件
        String clCode = bo.getClCode();
        IcesClientBo clientBo = new IcesClientBo();
        clientBo.setClCode(clCode);
        // 查找列表
        List<IcesClientVo> vos = clientService.queryList(clientBo);
        assert vos != null && vos.size() == 1;
        // 调用更新（设置字段在目标方法进行）
        clientBo.setClCode(null);
        clientBo.setClId(vos.get(0).getClId());
        clientBo.setClStat(vos.get(0).getClStat());
        clientService.updateByBo(clientBo);
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
    private void validEntityBeforeSave(IcesClientTrade entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户贸易信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
