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
import com.ruoyi.system.domain.bo.IcesRefundRecordBo;
import com.ruoyi.system.domain.vo.IcesRefundRecordVo;
import com.ruoyi.system.domain.IcesRefundRecord;
import com.ruoyi.system.mapper.IcesRefundRecordMapper;
import com.ruoyi.system.service.IIcesRefundRecordService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 退货记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@RequiredArgsConstructor
@Service
public class IcesRefundRecordServiceImpl implements IIcesRefundRecordService {

    private final IcesRefundRecordMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询退货记录
     */
    @Override
    public IcesRefundRecordVo queryById(Long rrId){
        return baseMapper.selectVoById(rrId);
    }

    /**
     * 查询退货记录列表
     */
    @Override
    public TableDataInfo<IcesRefundRecordVo> queryPageList(IcesRefundRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesRefundRecord> lqw = buildQueryWrapper(bo);
        Page<IcesRefundRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询退货记录列表
     */
    @Override
    public List<IcesRefundRecordVo> queryList(IcesRefundRecordBo bo) {
        LambdaQueryWrapper<IcesRefundRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesRefundRecord> buildQueryWrapper(IcesRefundRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesRefundRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getRrCode()), IcesRefundRecord::getRrCode, bo.getRrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getOrCode()), IcesRefundRecord::getOrCode, bo.getOrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrCode()), IcesRefundRecord::getPrCode, bo.getPrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getClCode()), IcesRefundRecord::getClCode, bo.getClCode());
        lqw.eq(bo.getRrDelete() != null, IcesRefundRecord::getRrDelete, bo.getRrDelete());
        lqw.eq(StringUtils.isNotBlank(bo.getRrMan()), IcesRefundRecord::getRrMan, bo.getRrMan());
        lqw.eq(StringUtils.isNotBlank(bo.getRrDate()), IcesRefundRecord::getRrDate, bo.getRrDate());
        return lqw;
    }

    /**
     * 新增退货记录
     */
    @Override
    public Boolean insertByBo(IcesRefundRecordBo bo) {
        bo.setRrCode(codeService.insertByType("RefundRecord"));
        // 填入办理人信息
        String cMan = getLoginUsername();
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        bo.setRrMan(cMan);
        bo.setRrDate(cDate);
        IcesRefundRecord add = BeanUtil.toBean(bo, IcesRefundRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRrId(add.getRrId());
        }
        return flag;
    }

    /**
     * 修改退货记录
     */
    @Override
    public Boolean updateByBo(IcesRefundRecordBo bo) {
        IcesRefundRecord update = BeanUtil.toBean(bo, IcesRefundRecord.class);
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
    private void validEntityBeforeSave(IcesRefundRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除退货记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
