package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipment;
import com.ruoyi.system.domain.bo.IcesEquipmentModelBo;
import com.ruoyi.system.domain.vo.IcesEquipmentVo;
import com.ruoyi.system.domain.bo.IcesEquipmentBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备Service接口
 *
 * @author ruoyi
 * @date 2025-02-28
 */
public interface IIcesEquipmentService {

    /**
     * 根据设备ID查询设备信息
     *
     * @param eqId 设备ID
     * @return 设备信息
     */
    IcesEquipmentVo queryById(Long eqId);

    /**
     * 分页查询设备列表
     *
     * @param bo 设备查询条件
     * @param pageQuery 分页参数
     * @return 设备列表信息
     */
    TableDataInfo<IcesEquipmentVo> queryPageList(IcesEquipmentBo bo, PageQuery pageQuery);

    /**
     * 审核端查询设备状态
     * @param bo
     * @param pageQuery
     * @return
     */

    TableDataInfo<IcesEquipmentVo> queryReviewList(IcesEquipmentBo bo, PageQuery pageQuery);

    /**
     * 查询设备列表，不分页
     *
     * @param bo 设备查询条件
     * @return 设备列表
     */
    List<IcesEquipmentVo> queryList(IcesEquipmentBo bo);

    /**
     * 新增设备信息
     *
     * @param bo 设备新增参数
     * @return 新增结果，true表示成功，false表示失败
     */
    Boolean insertByBo(IcesEquipmentBo bo);

    /**
     * 修改设备信息
     *
     * @param bo 设备修改参数
     * @return 修改结果，true表示成功，false表示失败
     */
    Boolean updateByBo(IcesEquipmentBo bo);

    /**
     * 校验并批量删除设备信息
     *
     * @param ids 设备ID集合
     * @param isValid 是否进行有效性校验，true表示校验，false表示不校验
     * @return 删除结果，true表示成功，false表示失败
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
