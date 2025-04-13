package com.ruoyi.heart_beat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class IcesNowTaskStatus {
    @JsonProperty("task_id")
    private String taskId;
    @JsonProperty("op_done")
    private List<String> opDone;
    @JsonProperty("op_doing")
    private List<String> opDoing;
    @JsonProperty("op_undo")
    private List<String> opUndo;
}
