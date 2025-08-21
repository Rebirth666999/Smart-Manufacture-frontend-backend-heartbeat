package com.ruoyi.system.domain.dto;

import lombok.Data;
import java.util.List;

/**
 * 用于接收前端分析结果的DTO
 */
@Data
public class ContentAnalysisDTO {
    private Long exrId;
    private int code;
    private List<ContentItemDTO> data;
    private String msg;
    private DetailDTO detail;

    @Data
    public static class ContentItemDTO {
        private String content;
        private String type;
        private String id;
        private String role;
        // 其他字段保持与前端返回一致
    }

    @Data
    public static class DetailDTO {
        private String logid;
    }
}
