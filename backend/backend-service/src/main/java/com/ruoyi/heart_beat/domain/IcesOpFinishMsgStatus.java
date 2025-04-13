package com.ruoyi.heart_beat.domain;

import lombok.Data;

import java.util.Map;
@Data
public class IcesOpFinishMsgStatus {
    //这个实体类是为了定义一个设备操作完成后，存至数据库的desc字段的内容
    private String opID;
    private Map<String, Object> status;
    private String opName;
    private String taskId;

}
