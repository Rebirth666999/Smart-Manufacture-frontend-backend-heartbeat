package com.ruoyi.heart_beat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;
@Data
public class IcesHeartbeat {
    private Map<String, Object> status;
    @JsonProperty("eq_id")
    private String eqId;
    @JsonProperty("eq_name")
    private String eqName;
}
