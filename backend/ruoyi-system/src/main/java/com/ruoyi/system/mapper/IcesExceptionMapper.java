package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.IcesException;
import com.ruoyi.system.domain.vo.IcesExceptionVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 异常Mapper接口
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Mapper
public interface IcesExceptionMapper extends BaseMapperPlus<IcesExceptionMapper, IcesException, IcesExceptionVo> {

}
