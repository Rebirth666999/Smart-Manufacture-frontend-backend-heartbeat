package com.ruoyi.heart_beat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

//这是“根类”，由于接收的json字符串有多个key-value，对于每个大key，设置一个类，大key下的其他分支都卸载该类中
@Data
public class IcesHeartbeatMessage {
    //当前控制节点任务队列中保存的所有任务的执行情况
    @JsonProperty("tasks_status")
    private IcesTasksStatus tasksStatus;
    //正在执行的任务情况
    @JsonProperty("now_task_status")
    private IcesNowTaskStatus nowTaskStatus;
    //当前正在执行的任务所涉及的所有设备的心跳信息
    @JsonProperty("heartbeat")
    private List<IcesHeartbeat> heartbeat;
    //当前任务中设备操作执行完毕的信息
    @JsonProperty("op_finish_msg")
    private List<IcesOpFinishMsg> opFinishMsg;
    //错误信息
    @JsonProperty("error")
    private String error;//后续可能会改为List
    //任务队列信息，用于数据大屏，后续可以从数据库中获得
    @JsonProperty("task_info")
    private List<IcesTaskInfo> taskInfo;
}
