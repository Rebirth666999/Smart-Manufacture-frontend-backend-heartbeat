package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipmentAtomOperation;
import com.ruoyi.system.domain.vo.IcesEquipmentAtomOperationVo;
import com.ruoyi.system.domain.bo.IcesEquipmentAtomOperationBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备原子操作Service接口
 *
 * @author ruoyi
 * @date 2025-03-02
 */
public interface IIcesEquipmentAtomOperationService {

    /**
     * 查询设备原子操作
     */
    IcesEquipmentAtomOperationVo queryById(Long eaoId);

    /**
     * 查询设备原子操作列表
     */
    TableDataInfo<IcesEquipmentAtomOperationVo> queryPageList(IcesEquipmentAtomOperationBo bo, PageQuery pageQuery);

    /**
     * 查询设备原子操作列表
     */
    List<IcesEquipmentAtomOperationVo> queryList(IcesEquipmentAtomOperationBo bo);

    /**
     * 新增设备原子操作
     */
    Boolean insertByBo(IcesEquipmentAtomOperationBo bo);

    /**
     * 修改设备原子操作
     */
    Boolean updateByBo(IcesEquipmentAtomOperationBo bo);

    /**
     * 校验并批量删除设备原子操作信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
