package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesManufactureTask;
import com.ruoyi.system.domain.vo.IcesManufactureTaskVo;
import com.ruoyi.system.domain.bo.IcesManufactureTaskBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 生产任务Service接口
 *
 * @author ruoyi
 * @date 2025-03-17
 */
public interface IIcesManufactureTaskService {

    /**
     * 查询生产任务
     */
    IcesManufactureTaskVo queryById(Long mtId);

    /**
     * 查询生产任务列表
     */
    TableDataInfo<IcesManufactureTaskVo> queryPageList(IcesManufactureTaskBo bo, PageQuery pageQuery);

    /**
     * 查询生产任务列表
     */
    List<IcesManufactureTaskVo> queryList(IcesManufactureTaskBo bo);

    /**
     * 新增生产任务
     */
    Boolean insertByBo(IcesManufactureTaskBo bo);

    /**
     * 修改生产任务
     */
    Boolean updateByBo(IcesManufactureTaskBo bo);

    /**
     * 校验并批量删除生产任务信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
