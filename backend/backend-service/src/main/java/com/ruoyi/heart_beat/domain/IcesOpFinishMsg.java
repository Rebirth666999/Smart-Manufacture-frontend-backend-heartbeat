package com.ruoyi.heart_beat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.Map;
@Data
public class IcesOpFinishMsg {
    @JsonProperty("eq_id")
    private String eqId;
    @JsonProperty("eq_name")
    private String eqName;
    @JsonProperty("op_id")
    private String opId;
    @JsonProperty("op_name")
    private String opName;
    @JsonProperty("begin_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "GMT+8")
    private Date beginTime;
    @JsonProperty("finish_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "GMT+8")
    private Date finishTime;
    private Map<String, Object> status;

}
