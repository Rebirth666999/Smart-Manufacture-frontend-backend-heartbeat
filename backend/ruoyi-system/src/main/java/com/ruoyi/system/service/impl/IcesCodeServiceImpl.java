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
import com.ruoyi.system.domain.bo.IcesCodeBo;
import com.ruoyi.system.domain.vo.IcesCodeVo;
import com.ruoyi.system.domain.IcesCode;
import com.ruoyi.system.mapper.IcesCodeMapper;
import com.ruoyi.system.service.IIcesCodeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 业务编码Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@RequiredArgsConstructor
@Service
public class IcesCodeServiceImpl implements IIcesCodeService {

    private final IcesCodeMapper baseMapper;

    // 按类型新增业务编码
    @Override
    public String insertByType(String type) {
        // 筛选此类型的所有编码
        // 构造筛选条件
        IcesCode code = new IcesCode();
        code.setCoType(type);
        LambdaQueryWrapper<IcesCode> lqw = Wrappers.lambdaQuery();
        lqw.eq(code.getCoType() != null, IcesCode::getCoType, code.getCoType());
        // 筛选列表
        List<IcesCode> codeList = baseMapper.selectList(lqw);
        // 取出最大值
        Long maxNum = codeList.stream().mapToLong(IcesCode::getCoNum).max().orElse(0L);
        // 确定编码
        code.setCoNum(maxNum + 1);
        baseMapper.insert(code);
        // 构造字符串返回
        return code.getCoType() + String.format("%05d", code.getCoNum());
    }
}
