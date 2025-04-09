package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.IcesExceptionRecord;
import com.ruoyi.system.domain.vo.IcesExceptionRecordVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 异常记录Mapper接口
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Mapper
public interface IcesExceptionRecordMapper extends BaseMapperPlus<IcesExceptionRecordMapper, IcesExceptionRecord, IcesExceptionRecordVo> {

}
