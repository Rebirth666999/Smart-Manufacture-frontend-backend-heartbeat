package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesArea;
import com.ruoyi.system.domain.vo.IcesAreaVo;
import com.ruoyi.system.domain.bo.IcesAreaBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 车间Service接口
 *
 * @author ruoyi
 * @date 2025-02-09
 */
public interface IIcesAreaService {

    /**
     * 查询车间
     */
    IcesAreaVo queryById(Long arId);

    /**
     * 查询车间列表
     */
    TableDataInfo<IcesAreaVo> queryPageList(IcesAreaBo bo, PageQuery pageQuery);

    /**
     * 查询车间列表
     */
    List<IcesAreaVo> queryList(IcesAreaBo bo);

    /**
     * 新增车间
     */
    Boolean insertByBo(IcesAreaBo bo);

    /**
     * 修改车间
     */
    Boolean updateByBo(IcesAreaBo bo);

    /**
     * 校验并批量删除车间信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
