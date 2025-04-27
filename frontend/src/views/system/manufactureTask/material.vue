<template>
  <div class="app-container">
    <el-table
      v-loading="loading"
      :data="materialDemand"
    >
      <el-table-column label="原料" align="center" prop="maCode">
        <template slot-scope="scope">
          {{ parseMaCode(scope.row.maCode) }}
        </template>
      </el-table-column>
      <el-table-column label="仓库" align="center" prop="msCode">
        <template slot-scope="scope">
          {{ parseMsCode(scope.row.msCode) }}
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="pmUnit" />
      <el-table-column label="总需求" align="center" prop="pmDemand" />
      <el-table-column label="现库存" align="center" prop="mssStock" />
      <el-table-column label="库存情况" align="center" prop="mssStock">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.mssStock > 1.2 * scope.row.pmDemand + 1" type="success">库存充足</el-tag>
          <el-tag v-else-if="scope.row.mssStock >= scope.row.pmDemand" type="warning">库存紧张</el-tag>
          <el-tag v-else type="danger">库存不足</el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { listManufactureTask } from "@/api/system/manufactureTask";
import { listProcessMaterial } from "@/api/system/processStep";
import { listMaterialStock } from "@/api/system/materialStock";
import { listMaterialStore } from "@/api/system/materialStore";
import { listMaterial } from "@/api/system/material";

// TODO
// 找到选定生产计划的所有任务
// 找到每个任务的工艺流程
// 找到工艺流程的原料需求
// 根据任务中的计划产品数量，计算任务的原料需求
// 根据任务的原料仓库，将需求与仓库对应
// 完成统计后，展示各个仓库需要提供的原料数量，以及各个仓库的原料存量

export default {
  name: "ManufactureMaterial",
  props: {
    mpCode: {
      required: false
    }
  },
  data() {
    return {
      // 全局加载中遮罩
      loading: false,
      // 生产任务列表
      manufactureTaskList: [],
      // 生产任务所用的工艺流程
      procCodeSet: new Set(),
      // 所有工艺流程的原料需求
      processMaterialList: [],
      // 原料库存信息
      materialStockList: [],
      // 原料信息
      materialList: [],
      // 原料仓库信息
      materialStoreList: [],
      // 原料需求信息
      materialDemand: [],
    }
  },
  async created() {
    await this.getManufactureTask()
    await this.getProcessMaterial()
    await this.getMaterialStock()
    await this.getMaterial()
    await this.getMaterialStore()
    this.getDemand()
  },
  async activated() {
    await this.getManufactureTask()
    await this.getProcessMaterial()
    await this.getMaterialStock()
    await this.getMaterial()
    await this.getMaterialStore()
    this.getDemand()
  },
  methods: {
    /**
     * 获取选中生产计划下的生产任务信息
     * @author YangZY
     * @date 20250424
     */
    getManufactureTask() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufactureTask({ mpCode: this.mpCode }).then(response => {
          this.manufactureTaskList = response.rows
          // 获取所有工艺流程信息
          response.rows.forEach(task => {
            this.procCodeSet.add(task.procCode)
          })
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /**
     * 获取选中生产计划下的生产任务
     * 所用工艺流程的全部原料需求
     * @author YangZY
     * @date 20250424
     */
    getProcessMaterial() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listProcessMaterial().then(response => {
          this.processMaterialList = []
          // 筛选找到被使用的原料需求
          response.rows.forEach(processMaterial => {
            if (this.procCodeSet.has(processMaterial.procCode)) {
              this.processMaterialList.push(processMaterial)
            }
          })
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /**
     * 获取原料库存列表
     * @author YangZY
     * @date 20250424
     */
    getMaterialStock() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listMaterialStock().then(response => {
          this.materialStockList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /**
     * 获取原料列表
     * @author YangZY
     * @date 20250424
     */
    getMaterial() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listMaterial().then(response => {
          this.materialList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /**
     * 获取原料仓库列表
     * @author YangZY
     * @date 20250424
     */
    getMaterialStore() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listMaterialStore().then(response => {
          this.materialStoreList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /**
     * 获取所有生产任务的原料总需求
     * @author YangZY
     * @date 20250424
     */
    getDemand() {
      this.materialDemand = []
      this.manufactureTaskList.forEach(task => {
        // 当前生产任务执行一个产品的所有原料需求
        const demands = this.processMaterialList.filter(ele => ele.procCode === task.procCode)
        demands.forEach(demand => {
          // 是否已经存在
          // 检查原料、单位、仓库
          const idx = this.materialDemand.findIndex(ele => ele.maCode === demand.maCode && ele.pmUnit === demand.pmUnit && ele.msCode === task.msCode)
          if (idx !== -1) {
            // 已存在，直接加数量
            // 单次数量乘以执行次数
            this.materialDemand[idx].pmDemand += demand.pmDemand * task.mtQtyPlan
          } else {
            // 不存在，插入新内容
            // 找到原料库存信息
            const mss = this.materialStockList.find(ele => ele.maCode === demand.maCode && ele.msCode === task.msCode)
            this.materialDemand.push({
              maCode: demand.maCode,
              pmUnit: demand.pmUnit,
              msCode: task.msCode,
              pmDemand: demand.pmDemand * task.mtQtyPlan,
              mssStock: mss? mss.mssStock : 0
            })
          }
        })
      })
    },
    /**
     * 解析原料字段
     * @param {string} maCode 原料编码
     * @author YangZY
     * @date 20250424
     */
    parseMaCode(maCode) {
      const material = this.materialList.find(ele => ele.maCode === maCode)
      return material? material.maName : ''
    },
    /**
     * 解析原料仓库字段
     * @param {string} msCode 原料仓库编码
     * @author YangZY
     * @date 20250424
     */
    parseMsCode(msCode) {
      const store = this.materialStoreList.find(ele => ele.msCode === msCode)
      return store? store.msName : ''
    }
  }
}
</script>