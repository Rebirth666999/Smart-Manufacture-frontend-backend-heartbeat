package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProductStore;
import com.ruoyi.system.domain.vo.IcesProductStoreVo;
import com.ruoyi.system.domain.bo.IcesProductStoreBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 产品仓库Service接口
 *
 * @author ruoyi
 * @date 2025-04-17
 */
public interface IIcesProductStoreService {

    /**
     * 查询产品仓库
     */
    IcesProductStoreVo queryById(Long prsId);

    /**
     * 查询产品仓库列表
     */
    TableDataInfo<IcesProductStoreVo> queryPageList(IcesProductStoreBo bo, PageQuery pageQuery);

    /**
     * 查询产品仓库列表
     */
    List<IcesProductStoreVo> queryList(IcesProductStoreBo bo);

    /**
     * 新增产品仓库
     */
    Boolean insertByBo(IcesProductStoreBo bo);

    /**
     * 修改产品仓库
     */
    Boolean updateByBo(IcesProductStoreBo bo);

    /**
     * 校验并批量删除产品仓库信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
