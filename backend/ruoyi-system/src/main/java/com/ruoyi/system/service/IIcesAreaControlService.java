package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesAreaControl;
import com.ruoyi.system.domain.vo.IcesAreaControlVo;
import com.ruoyi.system.domain.bo.IcesAreaControlBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 主控节点Service接口
 *
 * @author ruoyi
 * @date 2025-02-19
 */
public interface IIcesAreaControlService {

    /**
     * 查询主控节点
     */
    IcesAreaControlVo queryById(Long acId);

    /**
     * 查询主控节点列表
     */
    TableDataInfo<IcesAreaControlVo> queryPageList(IcesAreaControlBo bo, PageQuery pageQuery);

    /**
     * 查询主控节点列表
     */
    List<IcesAreaControlVo> queryList(IcesAreaControlBo bo);

    /**
     * 新增主控节点
     */
    Boolean insertByBo(IcesAreaControlBo bo);

    /**
     * 修改主控节点
     */
    Boolean updateByBo(IcesAreaControlBo bo);

    /**
     * 校验并批量删除主控节点信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
