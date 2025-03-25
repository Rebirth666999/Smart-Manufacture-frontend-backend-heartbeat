package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesClientLevel;
import com.ruoyi.system.domain.vo.IcesClientLevelVo;
import com.ruoyi.system.domain.bo.IcesClientLevelBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 客户等级Service接口
 *
 * @author ruoyi
 * @date 2025-03-24
 */
public interface IIcesClientLevelService {

    /**
     * 查询客户等级
     */
    IcesClientLevelVo queryById(Long cllId);

    /**
     * 查询客户等级列表
     */
    TableDataInfo<IcesClientLevelVo> queryPageList(IcesClientLevelBo bo, PageQuery pageQuery);

    /**
     * 查询客户等级列表
     */
    List<IcesClientLevelVo> queryList(IcesClientLevelBo bo);

    /**
     * 新增客户等级
     */
    Boolean insertByBo(IcesClientLevelBo bo);

    /**
     * 修改客户等级
     */
    Boolean updateByBo(IcesClientLevelBo bo);

    /**
     * 校验并批量删除客户等级信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
