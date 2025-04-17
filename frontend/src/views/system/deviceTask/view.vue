<template>
  <process-viewer
    v-loading="viewerData.loading"
    :key="`designer-${viewerData.index}`"
    :xml="viewerData.bpmnXml"
    :style="{height: 'calc(100vh - 124.5px)', margin: '15px'}"
    :mode="4"
    :extraList="{ emList: viewerData.emList, moList: viewerData.moList, eqList: viewerData.eqList, eoList: viewerData.eoList, eosList: viewerData.eosList, eospaList: viewerData.eospaList, dtList: viewerData.dtList, dtpaList: viewerData.dtpaList, maList: viewerData.maList }"
  />
</template>
<script>
import { listManufactureTask, getManufactureTask, delManufactureTask, addManufactureTask, updateManufactureTask } from "@/api/system/manufactureTask";
import { listDeviceTask, saveDeviceTasks, listDeviceTaskParam, listDeviceTaskPrev, executeDeviceTask } from "@/api/system/deviceTask";

import { listArea } from "@/api/system/area";
import { listAreaControl } from "@/api/system/areaControl";
import { listMaterial } from "@/api/system/material";
import { listManufacturePlan } from "@/api/system/manufacturePlan";
import { listProcess, getBpmnXml } from "@/api/system/process";
import { listMaterialStore } from "@/api/system/materialStore";
import { listProductStore } from "@/api/system/productStore";

import { listEquipment } from "@/api/system/equipment";
import { listEquipmentOperation } from "@/api/system/equipmentOperation";
import { listModelOperation } from "@/api/system/modelOperation";
import { listEquipmentModel } from "@/api/system/equipmentModel";
import { listEquipmentOperationStep } from "@/api/system/equipmentOperationStep";
import { listEquipmentOperationStepParam } from "@/api/system/equipmentOperationStepParam";import { listEquipmentAtomOperation } from "@/api/system/equipmentAtomOperation";

import ProcessViewer from '@/components/ProcessViewerIndustry';

export default {
  name: "ManufactureTask",
  components: {
    ProcessViewer,
  },
  props: {
    mpCode: {
      required: false
    }
  },
  dicts: ['ices_manufacture_task_status'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 生产任务表格数据
      manufactureTaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mpCode: undefined,
        arCode: undefined,
        mtStat: undefined,
        mtPriority: undefined,
        mtDelete: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mtId: [
          { required: true, message: "生产任务不能为空", trigger: "blur" }
        ],
        mpCode: [
          { required: true, message: "所属生产计划不能为空", trigger: "blur" }
        ],
        arCode: [
          { required: true, message: "目标车间不能为空", trigger: "blur" }
        ],
        stCodeMa: [
          { required: true, message: "原料仓库不能为空", trigger: "blur" }
        ],
        stCodePr: [
          { required: true, message: "产品仓库不能为空", trigger: "blur" }
        ],
        mtEndPlan: [
          { required: true, message: "最晚结束时间不能为空", trigger: "blur" }
        ],
        mtPriority: [
          { required: true, message: "任务优先级不能为空", trigger: "blur" }
        ],
        mtQtyPlan: [
          { required: true, message: "计划产品数量不能为空", trigger: "blur" }
        ],
      },
      // 车间列表
      areaList: [],
      // 生产计划列表
      manufacturePlanList: [],
      // 工艺流程列表
      processList: [],
      // 原料仓库列表
      materialStoreList: [],
      // 产品仓库列表
      productStoreList: [],
      // 查看窗口是否打开
      viewerOpen: false,
      // 查看器相关数据
      viewerData: {
        title: '',
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
        dtpaList: [],    // 设备任务参数列表
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
    await this.getManufacturePlanList()
    await this.getProcessList()
    await this.getStoreList()
    await this.getReferenceList()
    const row = (await getManufactureTask(this.$route.query.mtId)).data
    this.currentManufactureTask = row
    // 找到生产计划
    const manufacturePlan = this.manufacturePlanList.find(ele => ele.mpCode === row.mpCode)
    // 找到工艺流程
    const process = this.processList.find(ele => ele.procCode === manufacturePlan.procCode)
    // 找到已生成的设备任务
    this.viewerData.dtList = (await listDeviceTask({ mtCode: row.mtCode })).rows
    this.viewerData.dtpaList = (await listDeviceTaskParam({ mtCode: row.mtCode })).rows
    // 找到设备
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
    await this.getManufacturePlanList()
    await this.getProcessList()
    await this.getStoreList()
    await this.getReferenceList()
    const row = (await getManufactureTask(this.$route.query.mtId)).data
    this.currentManufactureTask = row
    // 找到生产计划
    const manufacturePlan = this.manufacturePlanList.find(ele => ele.mpCode === row.mpCode)
    // 找到工艺流程
    const process = this.processList.find(ele => ele.procCode === manufacturePlan.procCode)
    // 找到已生成的设备任务
    this.viewerData.dtList = (await listDeviceTask({ mtCode: row.mtCode })).rows
    this.viewerData.dtpaList = (await listDeviceTaskParam({ mtCode: row.mtCode })).rows
    // 找到设备
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
        this.loading = true
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
        this.loading = false
        resolve()
      })
    },
    // 获取仓库列表
    getStoreList() {
      return new Promise(async (resolve, reject) => {
        this.loading = true
        try {
          this.materialStoreList = (await listMaterialStore()).rows
          this.productStoreList = (await listProductStore()).rows
        } catch (err) {
          reject()
        }
        this.loading = false
        resolve()
      })
    },
    // 获取工艺流程列表
    getProcessList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listProcess().then(response => {
          this.processList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 获取生产计划列表
    getManufacturePlanList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufacturePlan().then(response => {
          this.manufacturePlanList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 获取车间列表
    getAreaList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listArea().then(response => {
          this.areaList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
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
  }
};
</script>