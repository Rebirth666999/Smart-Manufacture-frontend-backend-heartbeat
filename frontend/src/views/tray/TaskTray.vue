<template>
  <div class="tray-container">
    <el-tree v-if="treeData.length > 0" :data="treeData" @node-click="handleNodeClick">
      <div slot-scope="{ node, data }">
        <span v-if="data.root">
          <el-tag size="mini">{{ data.children.length }}</el-tag>{{ data.label }}
        </span>
        <el-button v-else type="text">{{ data.label }}</el-button>
      </div>
    </el-tree>
    <el-empty v-else description="暂无需要处理的任务" />
  </div>
</template>
<script>
import { listOrderDemand } from "@/api/system/orderDemand";
import { listManufacturePlan } from "@/api/system/manufacturePlan";
import { listManufactureTask } from "@/api/system/manufactureTask";

// TODO
// 未完成分配的订单：生产计划没有涵盖全部产品需求
// 未完成分配的生产计划：生产任务没有涵盖全部的产品数量
// 未完成分配的生产任务：没有生成设备任务

export default {
  name: "TaskTray",
  data() {
    return {
      // 渲染用的数据列表
      treeData: [],
      // 是否存在未完成任务
      show: true,
      // 订单产品需求列表
      orderDemandList: [],
      // 生产计划列表
      manufacturePlanList: [],
      // 生产任务列表
      manufactureTaskList: []
    }
  },
  async created() {
    this.treeData = []
    await this.getOrderDemandList()
    await this.getManufacturePlanList()
    await this.getManufactureTaskList()
    this.calcOrder()
    this.calcManufacturePlan()
    this.calcManufactureTask()
  },
  async activated() {
    this.treeData = []
    await this.getOrderDemandList()
    await this.getManufacturePlanList()
    await this.getManufactureTaskList()
    this.calcOrder()
    this.calcManufacturePlan()
    this.calcManufactureTask()
  },
  methods: {
    /**
     * 查询订单产品需求
     * @author YangZY
     * @date 20250424
     */
    getOrderDemandList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listOrderDemand().then(response => {
          this.orderDemandList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /**
     * 查询生产计划
     * @author YangZY
     * @date 20250424
     */
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
    /**
     * 查询生产任务
     * @author YangZY
     * @date 20250424
     */
    getManufactureTaskList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufactureTask().then(response => {
          this.manufactureTaskList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /**
     * 检查是否存在未完成分配或分配不正确的订单
     * @author YangZY
     * @date 20250424
     */
    calcOrder() {
      const incompleteSet = new Set()
      const errorSet = new Set()
      this.orderDemandList.forEach(demand => {
        // 找到与产品需求对应的生产计划
        const plans = this.manufacturePlanList.filter(ele => ele.odCode === demand.odCode)
        let sum = 0
        plans.forEach(plan => sum += plan.mpQtyPlan)

        if (sum < demand.odDemand) {
          incompleteSet.add({
            label: demand.orCode,
            type: 'order'
          })
        } else if (sum > demand.odDemand) {
          errorSet.add({
            label: demand.orCode,
            type: 'order'
          })
        }
      })
      if (incompleteSet.size > 0) {
        this.treeData.push({
          label: '订单未完成分配',
          root: true,
          children: Array.from(incompleteSet)
        })
      }
      if (errorSet.size > 0) {
        this.treeData.push({
          label: '订单分配错误',
          root: true,
          children: Array.from(errorSet)
        })
      }
    },
    /**
     * 检查是否存在未完成分配或分配不正确的生产计划
     * @author YangZY
     * @date 20250424
     */
     calcManufacturePlan() {
      const incompleteSet = new Set()
      const errorSet = new Set()
      this.manufacturePlanList.forEach(plan => {
        // 找到计划下的所有生产任务
        const tasks = this.manufactureTaskList.filter(ele => ele.mpCode === plan.mpCode)
        let sum = 0
        tasks.forEach(task => sum += task.mtQtyPlan)

        if (sum < plan.mpQtyPlan) {
          incompleteSet.add({
            label: plan.mpCode,
            type: 'manufacturePlan'
          })
        } else if (sum > plan.mpQtyPlan) {
          errorSet.add({
            label: plan.mpCode,
            type: 'manufacturePlan'
          })
        }
      })
      if (incompleteSet.size > 0) {
        this.treeData.push({
          label: '生产计划未完成分配',
          root: true,
          children: Array.from(incompleteSet)
        })
      }
      if (errorSet.size > 0) {
        this.treeData.push({
          label: '生产计划分配错误',
          root: true,
          children: Array.from(errorSet)
        })
      }
    },
    /**
     * 检查是否存在未分配的生产任务
     * @author YangZY
     * @date 20250424
     */
     calcManufactureTask() {
      // 已发布，但未生成任务的生产任务
      const incompleteList = this.manufactureTaskList.filter(ele => ele.mtStat === '4')
      if (incompleteList.length > 0) {
        this.treeData.push({
          label: '生产任务未完成分配',
          root: true,
          children: incompleteList.map(task => {
            return {
              label: task.mpCode,
              type: 'manufacturePlan'
            }
          })
        })
      }
    },
    handleNodeClick(data) {
      if (data.type === 'order') {
        this.$router.push(`/order?orCode=${data.label}`)
      } else if (data.type === 'manufacturePlan') {
        this.$router.push(`/manufacture/manufacturePlan?mpCode=${data.label}`)
      }
    }
  }
}
</script>
<style scoped>
.tray-container {
  height: 300px;
  overflow: scroll;
}
</style>