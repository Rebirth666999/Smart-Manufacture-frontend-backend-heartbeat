package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProduct;
import com.ruoyi.system.domain.vo.IcesProductVo;
import com.ruoyi.system.domain.bo.IcesProductBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 产品Service接口
 *
 * @author ruoyi
 * @date 2025-04-17
 */
public interface IIcesProductService {

    /**
     * 查询产品
     */
    IcesProductVo queryById(Long prId);

    /**
     * 查询产品列表
     */
    TableDataInfo<IcesProductVo> queryPageList(IcesProductBo bo, PageQuery pageQuery);

    /**
     * 查询产品列表
     */
    List<IcesProductVo> queryList(IcesProductBo bo);

    /**
     * 新增产品
     */
    Boolean insertByBo(IcesProductBo bo);

    /**
     * 修改产品
     */
    Boolean updateByBo(IcesProductBo bo);

    /**
     * 校验并批量删除产品信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
