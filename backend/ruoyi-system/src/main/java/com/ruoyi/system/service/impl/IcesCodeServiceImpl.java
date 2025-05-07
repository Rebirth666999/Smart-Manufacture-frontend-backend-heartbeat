package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.IcesCode;
import com.ruoyi.system.mapper.IcesCodeMapper;
import com.ruoyi.system.service.IIcesCodeService;

import java.util.List;

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

    /**
     * 按类型新增编码
     * @param type 类型字符串
     * @return 编码字符串
     * @author YangZY
     * @time 20250423
     */
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
        return code.getCoType() + "-" + String.format("%05d", code.getCoNum());
    }

    /**
     * 检查编码是否合法
     *
     * @param type 类型
     * @param code 编码
     * @author YangZY
     * @date 20250423
     */
    @Override
    public void checkCode(String type, String code) {
        String pattern = "^" + type + "-\\d{5}$";
        String patternModify = "^" + type + "-\\d{5}(-D)+$";
        boolean normal = code.matches(pattern);
        boolean modify = code.matches(patternModify);
        if (!normal && !modify) {
            throw new RuntimeException("编码格式错误");
        } else {
            // 查找编码是否存在
            IcesCode query = new IcesCode();
            query.setCoType(type);
            query.setCoNum(Long.parseLong(code.split("-")[1]));
            LambdaQueryWrapper<IcesCode> lqw = Wrappers.lambdaQuery();
            lqw.eq(query.getCoType() != null, IcesCode::getCoType, query.getCoType());
            lqw.eq(query.getCoNum() != null, IcesCode::getCoNum, query.getCoNum());
            List<IcesCode> codeList = baseMapper.selectList(lqw);
            if (normal) {
                // 普通插入，需要确保不重复
                if (codeList.isEmpty()) {
                    baseMapper.insert(query);
                } else {
                    throw new RuntimeException("编码已存在");
                }
            } else {
                // 插入编辑后内容，需要确保存在
                if (codeList.isEmpty()) {
                    throw new RuntimeException("原编码不存在");
                }
            }
        }
    }
}
