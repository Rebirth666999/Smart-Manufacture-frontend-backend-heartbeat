package com.ruoyi.heart_beat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class IcesProcessRoute {
    @JsonProperty("prdId")
    private String prdId;
    @JsonProperty("prePrdId")
    private List<String> prePrdId;
    @JsonProperty("eqId")
    private String eqId;
    @JsonProperty("opId")
    private String opId;
    @JsonProperty("opParam")
    private Map<String, Object> opParam;
}
