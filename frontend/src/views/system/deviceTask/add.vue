<template>
  <process-viewer
    v-loading="viewerData.loading"
    :key="`designer-${viewerData.index}`"
    :xml="viewerData.bpmnXml"
    :style="{height: 'calc(100vh - 124.5px)', margin: '15px'}"
    :mode="3"
    :extraList="{ emList: viewerData.emList, moList: viewerData.moList, eqList: viewerData.eqList, eoList: viewerData.eoList, eosList: viewerData.eosList, eospaList: viewerData.eospaList, dtList: viewerData.dtList, dtpaList: viewerData.dtpaList, maList: viewerData.maList }"
    @saveTask="onSaveTask"
  />
</template>
<script>
import { listManufactureTask, getManufactureTask, delManufactureTask, addManufactureTask, updateManufactureTask } from "@/api/system/manufactureTask";
import { listDeviceTask, saveDeviceTasks, listDeviceTaskParam, listDeviceTaskPrev, executeDeviceTask } from "@/api/system/deviceTask";

import { listArea } from "@/api/system/area";
import { listAreaControl } from "@/api/system/areaControl";
import { listMaterial } from "@/api/system/material";
import { listProcess, getBpmnXml } from "@/api/system/process";
import { listMaterialStore } from "@/api/system/materialStore";
import { listProductStore } from "@/api/system/productStore";

import { listEquipment } from "@/api/system/equipment";
import { listEquipmentOperation } from "@/api/system/equipmentOperation";
import { listModelOperation } from "@/api/system/modelOperation";
import { listEquipmentModel } from "@/api/system/equipmentModel";
import { listEquipmentOperationStep } from "@/api/system/equipmentOperationStep";
import { listEquipmentOperationStepParam } from "@/api/system/equipmentOperationStepParam";

import ProcessViewer from '@/components/ProcessViewerIndustry';

// 本页面内容为下发设备任务
export default {
  name: "AddDeviceTask",
  components: {
    ProcessViewer,
  },
  dicts: ['ices_manufacture_task_status'],
  data() {
    return {
      // 车间列表
      areaList: [],
      // 工艺流程列表
      processList: [],
      // 原料仓库列表
      materialStoreList: [],
      // 产品仓库列表
      productStoreList: [],
      // 查看器相关数据
      viewerData: {
        loading: false,
        index: undefined,
        bpmnXml: '',
        emList: [],     // 设备模型列表
        moList: [],     // 模型操作列表
        eqList: [],     // 设备列表（满足在对应的车间）
        eoList: [],     // 设备操作列表
        eosList: [],    // 设备操作步骤列表
        eospaList: [],  // 设备操作步骤参数列表
        dtList: [],     // 设备任务列表
        dtpaList: [],   // 设备任务参数列表
        maList: []      // 原料列表
      },
      // 设备列表（全）
      eqList: [],
      // 当前选中的生产任务
      currentManufactureTask: null,
    };
  },
  async created() {
    this.viewerData.loading = true
    await this.getAreaList()
    await this.getMaterialList()
    await this.getProcessList()
    await this.getStoreList()
    await this.getReferenceList()
    const row = (await getManufactureTask(this.$route.query.mtId)).data
    this.currentManufactureTask = row
    // 找到工艺流程
    const process = this.processList.find(ele => ele.procCode === row.procCode)
    // 找到可分配的设备
    this.viewerData.eqList = this.eqList.filter(ele => ele.arCode === row.arCode)
    // 打开流程
    this.viewerData.index = process.procModel
    getBpmnXml(process.procModel).then(response => {
      this.viewerData.bpmnXml = response.data || ''
      this.viewerData.loading = false
    })
  },
  async activated() {
    this.viewerData.index = ''
    this.viewerData.loading = true
    await this.getAreaList()
    await this.getMaterialList()
    await this.getProcessList()
    await this.getStoreList()
    await this.getReferenceList()
    const row = (await getManufactureTask(this.$route.query.mtId)).data
    this.currentManufactureTask = row
    // 找到工艺流程
    const process = this.processList.find(ele => ele.procCode === row.procCode)
    // 找到可分配的设备
    this.viewerData.eqList = this.eqList.filter(ele => ele.arCode === row.arCode)
    // 打开流程
    this.viewerData.index = process.procModel
    getBpmnXml(process.procModel).then(response => {
      this.viewerData.bpmnXml = response.data || ''
      this.viewerData.loading = false
    })
  },
  methods: {
    // 获取流程信息参照所需的列表
    // 设备模型、模型操作、设备操作、设备
    getReferenceList() {
      return new Promise(async (resolve, reject) => {
        try {
          this.viewerData.emList = (await listEquipmentModel()).rows
          this.viewerData.moList = (await listModelOperation()).rows
          this.viewerData.eoList = (await listEquipmentOperation()).rows
          this.eqList = (await listEquipment()).rows
          this.viewerData.eosList = (await listEquipmentOperationStep()).rows
          this.viewerData.eospaList = (await listEquipmentOperationStepParam()).rows
        } catch (err) {
          reject()
        }
        resolve()
      })
    },
    // 获取仓库列表
    getStoreList() {
      return new Promise(async (resolve, reject) => {
        try {
          this.materialStoreList = (await listMaterialStore()).rows
          this.productStoreList = (await listProductStore()).rows
        } catch (err) {
          reject()
        }
        resolve()
      })
    },
    // 获取工艺流程列表
    getProcessList() {
      return new Promise((resolve, reject) => {
        listProcess().then(response => {
          this.processList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
        })
      })
    },
    // 获取车间列表
    getAreaList() {
      return new Promise((resolve, reject) => {
        listArea().then(response => {
          this.areaList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
        })
      })
    },
    // 查询产品列表
    getMaterialList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listMaterial().then(response => {
          this.viewerData.maList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 确认生成设备任务
    onSaveTask(steps) {
      this.$modal.confirm('是否生成设备任务？').then(() => {
        this.viewerData.loading = true
        return saveDeviceTasks({
          manufactureTask: this.currentManufactureTask,
          deviceTask: steps
        });
      }).then(() => {
        this.$modal.msgSuccess("生成设备任务成功");
        const mtId = this.currentManufactureTask.mtId;
        return getManufactureTask(mtId);
      }).then(response => {
        const task = response.data;
        task.mtStat = 'd';
        return updateManufactureTask(task);
      }).then(() => {
        this.close();
      }).catch(() => {
      }).finally(() => {
        this.viewerData.loading = false;
      });
    },
    // 关闭本页，返回到生产计划管理
    close() {
      const obj = { path: "/manufacture/manageManufacture" };
      this.$tab.closeOpenPage(obj);
    }
  }
};
</script>
