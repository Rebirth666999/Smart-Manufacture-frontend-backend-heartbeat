package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProcess;
import com.ruoyi.system.domain.vo.IcesProcessVo;
import com.ruoyi.system.domain.bo.IcesProcessBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 工艺流程Service接口
 *
 * @author ruoyi
 * @date 2025-03-10
 */
public interface IIcesProcessService {

    /**
     * 查询工艺流程
     */
    IcesProcessVo queryById(Long procId);

    /**
     * 查询工艺流程列表
     */
    TableDataInfo<IcesProcessVo> queryPageList(IcesProcessBo bo, PageQuery pageQuery);

    /**
     * 查询工艺流程列表
     */
    List<IcesProcessVo> queryList(IcesProcessBo bo);

    /**
     * 新增工艺流程
     */
    Boolean insertByBo(IcesProcessBo bo);

    /**
     * 修改工艺流程
     */
    Boolean updateByBo(IcesProcessBo bo);

    /**
     * 校验并批量删除工艺流程信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
