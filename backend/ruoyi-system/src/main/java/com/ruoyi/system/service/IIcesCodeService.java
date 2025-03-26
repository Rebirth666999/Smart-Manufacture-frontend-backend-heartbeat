package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesCode;
import com.ruoyi.system.domain.vo.IcesCodeVo;
import com.ruoyi.system.domain.bo.IcesCodeBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 业务编码Service接口
 *
 * @author ruoyi
 * @date 2025-03-26
 */
public interface IIcesCodeService {
    // 按类型新增业务编码
    String insertByType(String type);
}
