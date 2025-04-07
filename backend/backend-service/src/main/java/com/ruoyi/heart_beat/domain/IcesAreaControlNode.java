package com.ruoyi.heart_beat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IcesAreaControlNode {
    @JsonProperty("acId")
    private String acId;
    @JsonProperty("acCode")
    private String acCode;
    @JsonProperty("arCode")
    private String arCode;
    @JsonProperty("acName")
    private String acName;
    @JsonProperty("acIp")
    private String acIp;
    @JsonProperty("acDelete")
    private int acDelete;
    @JsonProperty("acDesc")
    private String acDesc;
}
