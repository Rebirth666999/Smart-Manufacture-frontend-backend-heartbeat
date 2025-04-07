package com.ruoyi.heart_beat.domain;

import lombok.Data;

import java.util.List;

@Data
public class IcesTaskInfo {
    private List<IcesAreaControlNode> areaControl;
    private IcesProductionTask productionTask;
    private List<IcesProcessRoute> processRoute;

}
