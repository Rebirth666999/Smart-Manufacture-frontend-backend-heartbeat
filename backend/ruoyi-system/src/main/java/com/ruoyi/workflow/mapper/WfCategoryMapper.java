package com.ruoyi.workflow.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.workflow.domain.WfCategory;
import com.ruoyi.workflow.domain.vo.WfCategoryVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程分类Mapper接口
 *
 * @author KonBAI
 * @date 2022-01-15
 */
@Mapper
public interface WfCategoryMapper extends BaseMapperPlus<WfCategoryMapper, WfCategory, WfCategoryVo> {

}
