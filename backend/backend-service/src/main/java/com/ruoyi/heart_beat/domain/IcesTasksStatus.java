package com.ruoyi.heart_beat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class IcesTasksStatus {
    @JsonProperty("task_undo")
    private List<String> taskUndo;
    @JsonProperty("task_doning")
    private List<String> taskDoning;
    @JsonProperty("task_done")
    private List<String> taskDone;

}
