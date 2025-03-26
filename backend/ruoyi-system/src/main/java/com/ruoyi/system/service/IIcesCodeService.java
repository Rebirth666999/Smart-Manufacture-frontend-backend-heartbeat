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

    /**
     * 查询业务编码
     */
    IcesCodeVo queryById(Long coId);

    /**
     * 查询业务编码列表
     */
    TableDataInfo<IcesCodeVo> queryPageList(IcesCodeBo bo, PageQuery pageQuery);

    /**
     * 查询业务编码列表
     */
    List<IcesCodeVo> queryList(IcesCodeBo bo);

    /**
     * 新增业务编码
     */
    Boolean insertByBo(IcesCodeBo bo);

    /**
     * 修改业务编码
     */
    Boolean updateByBo(IcesCodeBo bo);

    /**
     * 校验并批量删除业务编码信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
