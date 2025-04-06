package com.ruoyi.heart_beat.service;

import com.ruoyi.system.domain.IcesEquipmentRecord;

public interface IcesEquipmentHeartbeatSaveService {
    int saveHeartbeat(String heartbeatMsg);

    void test();
}
