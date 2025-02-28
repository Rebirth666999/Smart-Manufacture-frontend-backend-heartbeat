package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesModelOperation;
import com.ruoyi.system.domain.vo.IcesModelOperationVo;
import com.ruoyi.system.domain.bo.IcesModelOperationBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备模型操作Service接口
 *
 * @author ruoyi
 * @date 2025-02-28
 */
public interface IIcesModelOperationService {

    /**
     * 查询设备模型操作
     */
    IcesModelOperationVo queryById(Long moId);

    /**
     * 查询设备模型操作列表
     */
    TableDataInfo<IcesModelOperationVo> queryPageList(IcesModelOperationBo bo, PageQuery pageQuery);

    /**
     * 查询设备模型操作列表
     */
    List<IcesModelOperationVo> queryList(IcesModelOperationBo bo);

    /**
     * 新增设备模型操作
     */
    Boolean insertByBo(IcesModelOperationBo bo);

    /**
     * 修改设备模型操作
     */
    Boolean updateByBo(IcesModelOperationBo bo);

    /**
     * 校验并批量删除设备模型操作信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
