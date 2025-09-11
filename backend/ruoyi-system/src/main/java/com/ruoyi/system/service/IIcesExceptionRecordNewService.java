package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionRecordNew;
import com.ruoyi.system.domain.bo.IcesExceptionRecordAiBo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordBo;
import com.ruoyi.system.domain.vo.IcesExceptionRecordNewVo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordNewBo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordAiBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.system.domain.vo.IcesExceptionRecordVo;

import java.util.Collection;
import java.util.List;

/**
 * 异常记录（新）Service接口
 *
 * @author ruoyi
 * @date 2025-07-30
 */
public interface IIcesExceptionRecordNewService {

    /**
     * 查询异常记录（新）
     */
    IcesExceptionRecordNewVo queryById(Long exrId);

    /**
     * 查询异常记录（新）列表
     */
    TableDataInfo<IcesExceptionRecordNewVo> queryPageList(IcesExceptionRecordNewBo bo, PageQuery pageQuery);

    /**
     * 查询异常记录（新）列表
     */
    List<IcesExceptionRecordNewVo> queryList(IcesExceptionRecordNewBo bo);

    /**
     * 新增异常记录（新）
     */

    IcesExceptionRecordNewVo insertByBo(IcesExceptionRecordNewBo bo);

    /**
     * 新增异常记录
     */
    IcesExceptionRecordNewVo insertByBo(IcesExceptionRecordAiBo bo);

    /**
     * 修改异常记录（新）
     */
    Boolean updateByBo(IcesExceptionRecordNewBo bo);
    String startLifecycleNew(IcesExceptionRecordNewBo bo);

    String startLifecycle(IcesExceptionRecordNewBo bo);

    String startLifecycle(IcesExceptionRecordAiBo bo);


    /**
     * 校验并批量删除异常记录（新）信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 保存分析内容到异常记录
     * @param exrId 异常记录ID
     * @param analysisContent 分析内容
     * @return 是否保存成功
     */
    boolean saveAnalysisContent(Long exrId, String analysisContent,long exrStat);
}
