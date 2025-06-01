package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.bo.*;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class IcesDataScreenServcie {
    private final IIcesManufactureTaskService iIcesManufactureTaskService;
    private final IIcesManufacturePlanService iIcesManufacturePlanService;
    private final IIcesDeviceTaskService iIcesDeviceTaskService;
    private final IIcesEquipmentService iIcesEquipmentService;

    //根据生产计划查看生产任务完成情况
    public List<IcesManufactureTask> queryIcesManufactureTask(IcesManufacturePlanBo bo) {
        // 1. 参数校验
        if (bo == null || bo.getMpId() == null) {
            throw new IllegalArgumentException("生产计划ID不能为空");
        }

        // 2. 验证生产计划是否存在
        IcesManufacturePlanVo plan = iIcesManufacturePlanService.queryById(bo.getMpId());
        if (plan == null) {
            throw new RuntimeException("未找到对应的生产计划");
        }

        // 3. 根据生产计划ID查询关联的生产任务
        List<IcesManufactureTask> tasks = (List<IcesManufactureTask>) iIcesManufactureTaskService.queryById(bo.getMpId());

        // 4. 如果需要对任务进行排序或其他处理，可以在这里添加
        // 例如按创建时间排序
        if (tasks != null && !tasks.isEmpty()) {
            tasks.sort(Comparator.comparing(IcesManufactureTask::getCreateTime));
        }

        return tasks != null ? tasks : Collections.emptyList();
    }

    //根据车间查看设备任务完成情况
    public List<IcesDeviceTask> queryIcesDeviceTask(IcesAreaBo bo) {
        //1.参数校验
        if (bo == null || bo.getArId() == null) {
            throw new IllegalArgumentException("车间选择不能为空");
        }

        //2.验证设备任务是否存在
        IcesManufacturePlanVo plan = iIcesManufacturePlanService.queryById(bo.getArId());
        if (plan == null) {
            throw new RuntimeException("未找到对应的设备任务");
        }

        //3.根据生产计划ID查询关联的生产任务
        List<IcesDeviceTask> tasks = (List<IcesDeviceTask>) iIcesDeviceTaskService.queryById(plan.getMpId());

        //4.如果需要排序：
        if (tasks != null && !tasks.isEmpty()) {
            tasks.sort(Comparator.comparing(IcesDeviceTask::getCreateTime));
        }

        return tasks != null ? tasks : Collections.emptyList();
    }

    //根据生产任务查看设备任务完成情况
    public List<IcesDeviceTask> queryIcesDeviceTaskByMt(IcesManufactureTaskBo bo) {
        //1.参数校验
        if (bo == null || bo.getMtId() == null) {
            throw new IllegalArgumentException("设备任务不能为空");
        }

        //2.验证设备任务是否存在
        IcesManufacturePlanVo plan = iIcesManufacturePlanService.queryById(bo.getMtId());
        if (plan == null) {
            throw new RuntimeException("未找到对应的设备任务");
        }

        //3.根据生产计划ID查询关联的生产任务
        List<IcesDeviceTask> tasks = (List<IcesDeviceTask>) iIcesDeviceTaskService.queryById(plan.getMpId());

        //4.如果需要排序：
        if (tasks != null && !tasks.isEmpty()) {
            tasks.sort(Comparator.comparing(IcesDeviceTask::getCreateTime));
        }

        return tasks != null ? tasks : Collections.emptyList();
    }

    //根据订单查看生产计划完成情况
    public List<IcesManufacturePlan> queryIcesManufacturePlan(IcesOrderBo bo) {
        //1.参数校验
        if (bo == null || bo.getOrId() == null) {
            throw new IllegalArgumentException("设备任务不能为空");
        }

        //2.验证生产计划是否存在
        IcesManufacturePlanVo plan = iIcesManufacturePlanService.queryById(bo.getOrId());
        if (plan == null) {
            throw new RuntimeException("未找到对应的生产计划");
        }

        //3.根据订单ID查询关联的生产计划
        List<IcesManufacturePlan> tasks = (List<IcesManufacturePlan>) iIcesDeviceTaskService.queryById(plan.getMpId());

        //4.如果需要排序
        if (tasks != null && !tasks.isEmpty()) {
            tasks.sort(Comparator.comparing(IcesManufacturePlan::getCreateTime));
        }

        return tasks != null ? tasks : Collections.emptyList();
    }
}







