package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesStore;
import com.ruoyi.system.domain.vo.IcesStoreVo;
import com.ruoyi.system.domain.bo.IcesStoreBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 仓库Service接口
 *
 * @author ruoyi
 * @date 2025-03-26
 */
public interface IIcesStoreService {

    /**
     * 查询仓库
     */
    IcesStoreVo queryById(Long stId);

    /**
     * 查询仓库列表
     */
    TableDataInfo<IcesStoreVo> queryPageList(IcesStoreBo bo, PageQuery pageQuery);

    /**
     * 查询仓库列表
     */
    List<IcesStoreVo> queryList(IcesStoreBo bo);

    /**
     * 新增仓库
     */
    Boolean insertByBo(IcesStoreBo bo);

    /**
     * 修改仓库
     */
    Boolean updateByBo(IcesStoreBo bo);

    /**
     * 校验并批量删除仓库信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
