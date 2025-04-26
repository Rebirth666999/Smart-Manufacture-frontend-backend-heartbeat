package com.ruoyi.heart_beat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.heart_beat.domain.*;
import com.ruoyi.heart_beat.service.IcesEquipmentHeartbeatSaveService;
import com.ruoyi.system.domain.IcesDeviceTask;
import com.ruoyi.system.domain.IcesEquipment;
import com.ruoyi.system.domain.IcesEquipmentRecord;
import com.ruoyi.system.domain.bo.IcesDeviceTaskBo;
import com.ruoyi.system.domain.bo.IcesEquipmentBo;
import com.ruoyi.system.domain.bo.IcesEquipmentRecordBo;
import com.ruoyi.system.domain.vo.IcesDeviceTaskVo;
import com.ruoyi.system.mapper.IcesEquipmentRecordMapper;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesDeviceTaskService;
import com.ruoyi.system.service.IIcesEquipmentRecordService;
import com.ruoyi.system.service.IIcesEquipmentService;
import liquibase.pro.packaged.D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
//接收任务和执行任务的列表只能存入一次，没有处理desc字段，优化erTyprDict

@Service
public class IcesEquipmentHeartbeatSaveServiceImpl implements IcesEquipmentHeartbeatSaveService {
    @Autowired
    private IIcesEquipmentRecordService recordService;
    @Autowired
    private IIcesCodeService codeService;
    @Autowired
    private IIcesEquipmentService equipmentService;
    @Autowired
    private IIcesDeviceTaskService deviceTaskService;
    //事件类型代码
    private static Map<String,String> erTypeDict = new HashMap<>();
    //事件状态代码
    private static Map<String,String> erStatDict = new HashMap<>();
    //由于设备操作完成信息随设备心跳定时发送至后端，因此同一个设备操作完成信息可能接收两次，因此需要保存对于这个任务已经接收过的设备操作完成信息
    private static List<IcesOpFinishMsg> opFinishMsgSave=new ArrayList<>();
    //已接受到设备任务的设备任务列表
    private static List<String> eqOpReceived=new ArrayList<>();
    //正在执行设备任务的设备任务列表
    private static List<String> eqOpExecuting=new ArrayList<>();
    //保存当前发送心跳信息时设备节点在执执行的任务id
    private static String nowTaskId=new String();
    private static Map<String,IcesTaskInfo> tasksInfo=new HashMap<>();
    //控制节点对应的设备id
    private String controllerId="EquipmentModel-00008";
    // 已下发任务code
    private Set<String> dtRelease = new HashSet<>();
    // 已完成任务code
    private Set<String> dtDone = new HashSet<>();
    // 已完成任务code
    private Set<String> dtDoing = new HashSet<>();
    static {
        erTypeDict.put("进行中","1");
        erTypeDict.put("已完成","2");
        erStatDict.put("创建设备", "1");
        erStatDict.put("设备修改", "c");
        erStatDict.put("提交审核", "2");
        erStatDict.put("开始审核", "3");
        erStatDict.put("审核通过", "4");
        erStatDict.put("审核未通过", "5");
        erStatDict.put("设备心跳", "6");
        erStatDict.put("心跳丢失", "7");
        erStatDict.put("收到任务", "8");
        erStatDict.put("执行任务", "9");
        erStatDict.put("任务执行完成", "a");
        erStatDict.put("发生故障", "b");
    }
    @Override
    public int saveHeartbeat(String heartbeatMsg) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            //可能不对，当控制节点出错时可能不会执行这些代码
            IcesError icesError = objectMapper.readValue(heartbeatMsg, IcesError.class);
            System.out.println("!!!!!!!!!!!!!!!!!!ERROR HAPPENED!!!!!!!!!!!!!!!!!!!!!!!!!");
//            saveDB(controllerId,erTypeDict.get("已完成"),erStatDict.get("发生故障"),1,new Date(),new Date(),heartbeatMsg);
            return 0;
        }catch (Exception e){

        }
        try {
            System.out.println("-------------------------------------控制节点传回的心跳信息-------------------------------------");
            ObjectMapper objectMapper = new ObjectMapper();
            IcesHeartbeatMessage root = objectMapper.readValue(heartbeatMsg, IcesHeartbeatMessage.class);
            // 打印任务状态
            System.out.println("任务状态:");
            final List<String> taskUndo = root.getTasksStatus().getTaskUndo();
            final List<String> taskDoing = root.getTasksStatus().getTaskDoning();
            final List<String> taskDone = root.getTasksStatus().getTaskDone();

            System.out.println("   未完成的任务: " + taskUndo);
            System.out.println("   正在执行的任务: " + taskDoing);
            System.out.println("   已完成的任务: " + taskDone);
            System.out.println();

            // 打印任务信息
            System.out.println("任务信息");
            for (IcesTaskInfo taskInfo : root.getTaskInfo()) {
                System.out.println(" 任务id : " + taskInfo.getProductionTask().getPtId());
                if(!tasksInfo.containsKey(taskInfo.getProductionTask().getPtId())){
                    tasksInfo.put(taskInfo.getProductionTask().getPtId(),taskInfo);
                }
                for (IcesProcessRoute route : taskInfo.getProcessRoute()) {
                    System.out.println("  Process_Route: " + route.getPrdId() );
                    System.out.println("  EQ ID: " + route.getEqId());
                    System.out.println();
                }
            }

            // 打印当前任务状态
            //对于收到任务和执行任务，存入数据库的desc字段应该包含任务id和操作id
            System.out.println("当前任务状态");

            if(!nowTaskId.equals(root.getNowTaskStatus().getTaskId())){
                nowTaskId=root.getNowTaskStatus().getTaskId();
                //当一个新任务开始执行时，清空一些保存旧数据的变量，减轻内存压力
                flush();
            }
            final String taskId = root.getNowTaskStatus().getTaskId();
            final List<String> opDoing = root.getNowTaskStatus().getOpDoing();
            final List<String> opDone = root.getNowTaskStatus().getOpDone();
            final List<String> opUndo = root.getNowTaskStatus().getOpUndo();
            System.out.println("   当前正在执行的任务id: " + taskId);
            System.out.println("   正在执行的设备操作: " + opDoing);
            System.out.println("   执行完的设备操作: " + opDone);
            System.out.println("   未执行的设备操作: " + opUndo);
            System.out.println();
            for (String dtCode: opUndo) {
                if (!dtRelease.contains(dtCode)) {
                    dtRelease.add(dtCode);
                    IcesDeviceTaskBo deviceTaskBo = new IcesDeviceTaskBo();
                    deviceTaskBo.setDtCode(dtCode);
                    List<IcesDeviceTaskVo> vos = deviceTaskService.queryList(deviceTaskBo);
                    if (!vos.isEmpty()) {
                        deviceTaskBo.setDtId(vos.get(0).getDtId());
                        deviceTaskBo.setDtStat("2");
                        deviceTaskService.updateByBo(deviceTaskBo);
                    }
                }
            }
            for (String dtCode: opDoing) {
                if (!dtDoing.contains(dtCode)) {
                    dtDoing.add(dtCode);
                    IcesDeviceTaskBo deviceTaskBo = new IcesDeviceTaskBo();
                    deviceTaskBo.setDtCode(dtCode);
                    List<IcesDeviceTaskVo> vos = deviceTaskService.queryList(deviceTaskBo);
                    if (!vos.isEmpty()) {
                        deviceTaskBo.setDtId(vos.get(0).getDtId());
                        deviceTaskBo.setDtStat("3");
                        deviceTaskService.updateByBo(deviceTaskBo);
                    }
                }
            }
            for (String dtCode: opDone) {
                if (!dtDone.contains(dtCode)) {
                    dtDone.add(dtCode);
                    IcesDeviceTaskBo deviceTaskBo = new IcesDeviceTaskBo();
                    deviceTaskBo.setDtCode(dtCode);
                    List<IcesDeviceTaskVo> vos = deviceTaskService.queryList(deviceTaskBo);
                    if (!vos.isEmpty()) {
                        deviceTaskBo.setDtId(vos.get(0).getDtId());
                        deviceTaskBo.setDtStat("4");
                        deviceTaskService.updateByBo(deviceTaskBo);
                    }
                }
            }
            if (opUndo.isEmpty() && opDoing.isEmpty() && opDone.isEmpty()) {
                dtRelease.clear();
                dtDone.clear();
                dtDoing.clear();
            }
//            for (String s : opUndo) {
//                System.out.println("准备添加任务接收记录"+opUndo+eqOpReceived);
//                if(!eqOpReceived.contains(s)){
//                    System.out.println("添加任务接收记录");
//                    eqOpReceived.add(s);
//                    final IcesTaskInfo taskInfo = tasksInfo.get(taskId);
//                    for (IcesProcessRoute icesProcessRoute : taskInfo.getProcessRoute()) {
//                        if(s.equals(icesProcessRoute.getPrdId())){
//                            final ObjectMapper mapper = new ObjectMapper();
//                            final IcesOpTaskStatus icesOpTaskStatus = new IcesOpTaskStatus();
//                            icesOpTaskStatus.setOpId(icesProcessRoute.getOpId());
//                            icesOpTaskStatus.setTaskId(nowTaskId);
//                            icesOpTaskStatus.setPrdId(icesProcessRoute.getPrdId());
//                            final String desc = mapper.writeValueAsString(icesOpTaskStatus);
//                            saveDB(icesProcessRoute.getEqId(),erTypeDict.get("已完成"),erStatDict.get("收到任务"),1,new Date(),new Date(),desc);
//                            break;
//                        }
//                    }
//                }
//            }
//            for (String s : opDoing) {
//                if(!eqOpExecuting.contains(s)){
//                    System.out.println("添加设备执行记录");
//                    eqOpExecuting.add(s);
//                    //如果当前设备操作未加入接收队列，直接进入了执行队列
//                    boolean flag=false;
//                    if(!eqOpReceived.contains(s)){
//                        flag=true;
//                    }/*else {
//                        //当前任务已经在接收队列，现需要清除接收队列中的该任务
//                        eqOpReceived.
//                    }*/
//                    final IcesTaskInfo taskInfo = tasksInfo.get(taskId);
//                    for (IcesProcessRoute icesProcessRoute : taskInfo.getProcessRoute()) {
//                        if(s.equals(icesProcessRoute.getPrdId())){
//                            final ObjectMapper mapper = new ObjectMapper();
//                            final IcesOpTaskStatus icesOpTaskStatus = new IcesOpTaskStatus();
//                            icesOpTaskStatus.setOpId(icesProcessRoute.getOpId());
//                            icesOpTaskStatus.setTaskId(nowTaskId);
//                            icesOpTaskStatus.setPrdId(icesProcessRoute.getPrdId());
//                            final String desc = mapper.writeValueAsString(icesOpTaskStatus);
//                            saveDB(icesProcessRoute.getEqId(),erTypeDict.get("已完成"),erStatDict.get("执行任务"),1,new Date(),new Date(),desc);
//                            if(flag){
//                                System.out.println("添加任务接收记录");
//                                eqOpReceived.add(s);
//                                saveDB(icesProcessRoute.getEqId(),erTypeDict.get("已完成"),erStatDict.get("收到任务"),1,new Date(),new Date(),desc);
//                            }
//                            break;
//                        }
//                    }
//                }
//            }
            // 打印心跳信息
            System.out.println("心跳信息:");
//            for (IcesHeartbeat heartbeat : root.getHeartbeat()) {
//                String eqId=heartbeat.getEqId();
//                String eqNmae=heartbeat.getEqName();
//                String status=heartbeat.getStatus().toString();
//                System.out.println("   设备id: " +  eqId);
//                System.out.println("   设备名: " + eqNmae);
//                System.out.println("   当前状态: " + status);
//                saveDB(eqId,erTypeDict.get("已完成"),erStatDict.get("设备心跳"),1,new Date(),new Date(),status);
//                System.out.println();
//
//            }
            //打印操作完成信息
            System.out.println("任务完成信息");
//            for (IcesOpFinishMsg opFinishMsg : root.getOpFinishMsg()) {
//                // 将方法调用的结果保存为变量
//                String eqId = opFinishMsg.getEqId();
//                String eqName = opFinishMsg.getEqName();
//                String opId = opFinishMsg.getOpId();
//                String opName = opFinishMsg.getOpName();
//                Date beginTime = opFinishMsg.getBeginTime();
//                Date finishTime = opFinishMsg.getFinishTime();
//                Map<String, Object> status = opFinishMsg.getStatus();
//
//                // 使用变量进行打印
//                System.out.println("   设备id : " + eqId);
//                System.out.println("   设备名 : " + eqName);
//                System.out.println("   操作id : " + opId);
//                System.out.println("   操作名 : " + opName);
//                System.out.println("   开始时间 : " + beginTime);
//                System.out.println("   结束时间 : " + finishTime);
//                System.out.println("   结束信息 : " + status.toString());
//                System.out.println();
//                //构建存入数据库的dec字段
//                final ObjectMapper mapper = new ObjectMapper();
//                final IcesOpFinishMsgStatus icesOpFinishMsgStatus = new IcesOpFinishMsgStatus();
//                icesOpFinishMsgStatus.setStatus(status);
//                icesOpFinishMsgStatus.setOpID(opId);
//                icesOpFinishMsgStatus.setOpName(opName);
//                icesOpFinishMsgStatus.setTaskId(nowTaskId);
//                final String statusDB = mapper.writeValueAsString(icesOpFinishMsgStatus);
//                //因为在同一时刻，不会有一个设备完成两个相同的操作，因此可以用opFinishMsg来进行区分
//                if(!opFinishMsgSave.contains(opFinishMsg)){
//                   System.out.println("写入任务完成信息");
//                    saveDB(eqId,erTypeDict.get("已完成"),erStatDict.get("任务执行完成"),1,beginTime,finishTime,statusDB);
//                   opFinishMsgSave.add(opFinishMsg);
//                }
//            }
            //打印错误信息
            System.out.println("错误信息");
            System.out.println(   root.getError());
            System.out.println();

            System.out.println("---------------------------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("当前心跳信息格式错误，可能是因为当前控制节点未接收到任务");
        }finally {

        }
    return 0;
    }

    private void flush() {
        opFinishMsgSave.clear();
        eqOpReceived.clear();
        eqOpExecuting.clear();
        System.out.println("eqOpExecuting: "+eqOpExecuting);
        System.out.println("eqOpReceived: "+eqOpReceived);

    }

    private void saveDB(String eqCode, String erType, String erStat, int erLevel, Date erBeginTime ,Date erEndTime,String desc){
        IcesEquipmentRecordBo equipmentRecord = new IcesEquipmentRecordBo();
        String equipmentRecordCode = codeService.insertByType("EquipmentRecord");
        equipmentRecord.setErCode(equipmentRecordCode);
        equipmentRecord.setEqCode(eqCode);
        equipmentRecord.setErType(erType);
        equipmentRecord.setErStat(erStat);
        equipmentRecord.setErLevel(erLevel);
        equipmentRecord.setErBegin(erBeginTime);
        equipmentRecord.setErEnd(erEndTime);
        equipmentRecord.setErDesc(desc);
        equipmentRecord.setCreateBy("controller_node");
        equipmentRecord.setUpdateBy("controller_node");
        recordService.insertByBo(equipmentRecord);
        //更新设备最后一次通讯时间
        final IcesEquipment icesEquipment = equipmentService.selectByEqCode(eqCode);
        icesEquipment.setEqCommunicateTime(new Date());
        equipmentService.update(icesEquipment);

    }
    @Override
    public void test() {
        System.out.println("go in method");
    }
}
