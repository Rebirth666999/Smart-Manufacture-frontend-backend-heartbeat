package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.IcesDeviceTask;
import com.ruoyi.system.domain.IcesManufacturePlan;
import com.ruoyi.system.domain.IcesManufactureTask;
import com.ruoyi.system.domain.bo.IcesAreaBo;
import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.domain.bo.IcesManufactureTaskBo;
import com.ruoyi.system.domain.bo.IcesOrderBo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/DataScreen")
public class IcesDataScreenController extends BaseController {

    @Autowired
    private IcesDataScreenServcie icesDataScreenServcie;

    @Autowired
    private IIcesManufacturePlanService IcesManufacturePlanService;

    //根据生产计划查看生产任务完成情况
    @PostMapping("/tasks")
    public List<IcesManufactureTask> MtTaskByMyPlan(@RequestParam Long planId) {
        IcesManufacturePlanBo planBo = new IcesManufacturePlanBo();
        IcesManufacturePlanVo planVo = IcesManufacturePlanService.queryById(planId);
        BeanUtils.copyProperties(planVo, planBo);
        List<IcesManufactureTask> list;
        list = icesDataScreenServcie.queryIcesManufactureTask(planBo);//获取任务数列表
        return list;
    }
    // 根据车间查看设备任务完成情况
    @PostMapping("/device-tasks/area")
    public List<IcesDeviceTask> getDeviceTasksByArea(@RequestBody IcesAreaBo areaBo) {
        return icesDataScreenServcie.queryIcesDeviceTask(areaBo);
    }

    // 根据生产任务查看设备任务完成情况
    @PostMapping("/device-tasks/mtask")
    public List<IcesDeviceTask> getDeviceTasksByManufactureTask(@RequestBody IcesManufactureTaskBo taskBo) {
        return icesDataScreenServcie.queryIcesDeviceTaskByMt(taskBo);
    }

    // 根据订单查看生产计划完成情况
    @PostMapping("/plans/order")
    public List<IcesManufacturePlan> getPlansByOrder(@RequestBody IcesOrderBo orderBo) {
        return icesDataScreenServcie.queryIcesManufacturePlan(orderBo);
    }



}
