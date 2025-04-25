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
import com.ruoyi.system.domain.bo.IcesManufacturePlanMainBo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanMainVo;
import com.ruoyi.system.domain.IcesManufacturePlanMain;
import com.ruoyi.system.mapper.IcesManufacturePlanMainMapper;
import com.ruoyi.system.service.IIcesManufacturePlanMainService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 生产计划(主)Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-25
 */
@RequiredArgsConstructor
@Service
public class IcesManufacturePlanMainServiceImpl implements IIcesManufacturePlanMainService {

    private final IcesManufacturePlanMainMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询生产计划(主)
     */
    @Override
    public IcesManufacturePlanMainVo queryById(Long mpmId){
        return baseMapper.selectVoById(mpmId);
    }

    /**
     * 查询生产计划(主)列表
     */
    @Override
    public TableDataInfo<IcesManufacturePlanMainVo> queryPageList(IcesManufacturePlanMainBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesManufacturePlanMain> lqw = buildQueryWrapper(bo);
        Page<IcesManufacturePlanMainVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询生产计划(主)列表
     */
    @Override
    public List<IcesManufacturePlanMainVo> queryList(IcesManufacturePlanMainBo bo) {
        LambdaQueryWrapper<IcesManufacturePlanMain> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesManufacturePlanMain> buildQueryWrapper(IcesManufacturePlanMainBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesManufacturePlanMain> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMpmCode()), IcesManufacturePlanMain::getMpmCode, bo.getMpmCode());
        lqw.eq(StringUtils.isNotBlank(bo.getOrCode()), IcesManufacturePlanMain::getOrCode, bo.getOrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMpmStat()), IcesManufacturePlanMain::getMpmStat, bo.getMpmStat());
        lqw.eq(bo.getMpmDelete() != null, IcesManufacturePlanMain::getMpmDelete, bo.getMpmDelete());
        return lqw;
    }

    /**
     * 新增生产计划(主)
     */
    @Override
    public Boolean insertByBo(IcesManufacturePlanMainBo bo) {
        bo.setMpmCode(codeService.insertByType("ManufacturePlanMain"));
        String cMan = getLoginUsername();
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        bo.setMpmCman(cMan);
        bo.setMpmCdate(cDate);
        IcesManufacturePlanMain add = BeanUtil.toBean(bo, IcesManufacturePlanMain.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMpmId(add.getMpmId());
        }
        return flag;
    }

    /**
     * 修改生产计划(主)
     */
    @Override
    public Boolean updateByBo(IcesManufacturePlanMainBo bo) {
        String mMan = getLoginUsername();
        String mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        bo.setMpmMman(mMan);
        bo.setMpmMdate(mDate);
        IcesManufacturePlanMain update = BeanUtil.toBean(bo, IcesManufacturePlanMain.class);
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
    private void validEntityBeforeSave(IcesManufacturePlanMain entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除生产计划(主)
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
