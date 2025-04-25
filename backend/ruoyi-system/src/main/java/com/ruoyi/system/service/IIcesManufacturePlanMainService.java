package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.IcesManufacturePlanMainVo;
import com.ruoyi.system.domain.bo.IcesManufacturePlanMainBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 生产计划(主)Service接口
 *
 * @author ruoyi
 * @date 2025-04-25
 */
public interface IIcesManufacturePlanMainService {

    /**
     * 查询生产计划(主)
     */
    IcesManufacturePlanMainVo queryById(Long mpmId);

    /**
     * 查询生产计划(主)列表
     */
    TableDataInfo<IcesManufacturePlanMainVo> queryPageList(IcesManufacturePlanMainBo bo, PageQuery pageQuery);

    /**
     * 查询生产计划(主)列表
     */
    List<IcesManufacturePlanMainVo> queryList(IcesManufacturePlanMainBo bo);

    /**
     * 新增生产计划(主)
     */
    IcesManufacturePlanMainVo insertByBo(IcesManufacturePlanMainBo bo);

    /**
     * 修改生产计划(主)
     */
    Boolean updateByBo(IcesManufacturePlanMainBo bo);

    /**
     * 校验并批量删除生产计划(主)信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
