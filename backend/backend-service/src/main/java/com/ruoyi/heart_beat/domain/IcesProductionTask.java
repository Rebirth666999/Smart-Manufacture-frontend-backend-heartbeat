package com.ruoyi.heart_beat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class IcesProductionTask {
    @JsonProperty("ptId")
    private String ptId;
    @JsonProperty("ptLatestEndtime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "GMT+8")
    private Date ptLatestEndtime;
    @JsonProperty("ptNum")
    private int ptNum;
    @JsonProperty("ptPriority")
    private int ptPriority;
    private boolean preemptive;
}
