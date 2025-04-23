package com.ruoyi.system.service;

/**
 * 业务编码Service接口
 *
 * @author ruoyi
 * @date 2025-03-26
 */
public interface IIcesCodeService {
    // 按类型新增业务编码
    String insertByType(String type);
    // 检查编码是否合法
    void checkCode(String type, String code);
}
