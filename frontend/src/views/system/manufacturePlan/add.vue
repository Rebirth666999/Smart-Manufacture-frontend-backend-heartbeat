<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="110px">
      <el-form-item label="所属订单" prop="orCode">
        <el-select v-model="form.orCode" placeholder="请选择订单" @change="selectOrder">
          <el-option v-for="item in orderList" :key="item.orCode" :label="item.orName" :value="item.orCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="maCode">
        <span slot="label">
          <el-tooltip placement="top">
            <div slot="content">
              <div>须先选择订单，才能够加载订单的产品需求</div>
            </div>
            <i class="el-icon-question"></i>
          </el-tooltip>
          产品
        </span>
        <el-select v-model="form.maCode" placeholder="请选择产品" @change="selectProduct">
          <el-option v-for="item in productList" :key="item.maCode" :label="item.maName" :value="item.maCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="procCode">
        <span slot="label">
          <el-tooltip placement="top">
            <div slot="content">
              <div>须先选择产品，才能够加载产品的工艺流程</div>
            </div>
            <i class="el-icon-question"></i>
          </el-tooltip>
          工艺流程
        </span>
        <el-select v-model="form.procCode" placeholder="请选择工艺流程">
          <el-option v-for="item in processList" :key="item.procCode" :label="item.procName" :value="item.procCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="最晚结束时间" prop="mpEndPlan">
        <el-date-picker clearable v-model="form.mpEndPlan" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="请选择最晚结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="优先级" prop="mpPriority">
        <el-input v-model="form.mpPriority" placeholder="请输入优先级" />
      </el-form-item>
      <el-form-item label="计划产品数量" prop="mpQtyPlan">
        <el-input v-model="form.mpQtyPlan" placeholder="请输入计划产品数量" />
      </el-form-item>
      <el-form-item label="描述" prop="mpDesc">
        <el-input v-model="form.mpDesc" type="textarea" placeholder="请输入内容" />
      </el-form-item>
    </el-form>
    <div>
      <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { listManufacturePlan, getManufacturePlan, delManufacturePlan, addManufacturePlan, updateManufacturePlan } from "@/api/system/manufacturePlan";
import { listProcess } from "@/api/system/process";
import { listOrder } from "@/api/system/order";
import { listMaterial } from "@/api/system/material";
import { listOrderDemand } from "@/api/system/orderDemand";

export default {
  name: "ManufacturePlan",
  dicts: ['ices_manufacture_plan_status'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mpId: [
          { required: true, message: "生产计划不能为空", trigger: "blur" }
        ],
        orCode: [
          { required: true, message: "所属订单不能为空", trigger: "blur" }
        ],
        procCode: [
          { required: true, message: "工艺流程不能为空", trigger: "blur" }
        ],
        mpEndPlan: [
          { required: true, message: "最晚结束时间不能为空", trigger: "blur" }
        ],
        mpPriority: [
          { required: true, message: "优先级不能为空", trigger: "blur" }
        ],
        mpQtyPlan: [
          { required: true, message: "产品数量不能为空", trigger: "blur" }
        ],
      },
      // 所有工艺流程
      processListFull: [],
      // 供选择的工艺流程
      processList: [],
      // 订单列表
      orderList: [],
      // 全部产品列表
      productListFull: [],
      // 供选择的产品列表
      productList: [],
      // 所有订单的产品需求
      orderDemandList: [],
    };
  },
  async created() {
    this.loading = true;
    await this.getProcessList();
    await this.getOrderList();
    await this.getProductList();
    await this.getOrderDemandList();
    this.reset();
    if (this.$route.query.mpId) {
      this.processList = this.processListFull
      getManufacturePlan(this.$route.query.mpId).then(response => {
        this.form = response.data;
        this.selectOrder(this.form.orCode)
        this.loading = false;
      });
    } else {
      this.loading = false;
    }
  },
  async activated() {
    this.loading = true;
    await this.getProcessList();
    await this.getOrderList();
    await this.getProductList();
    await this.getOrderDemandList();
    this.reset();
    if (this.$route.query.mpId) {
      this.processList = this.processListFull
      getManufacturePlan(this.$route.query.mpId).then(response => {
        this.form = response.data;
        this.selectOrder(this.form.orCode)
        this.loading = false;
      });
    } else {
      this.loading = false;
    }
  },
  methods: {
    // 查询订单产品需求
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
    // 查询产品列表
    getProductList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listMaterial({ maType: '2' }).then(response => {
          this.productListFull = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询工艺流程列表
    getProcessList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listProcess().then(response => {
          // 全列表
          this.processListFull = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询产品列表
    getOrderList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listOrder().then(response => {
          this.orderList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 取消按钮
    cancel() {
      this.close();
    },
    // 表单重置
    reset() {
      this.form = {
        mpId: undefined,
        orCode: undefined,
        procCode: undefined,
        mpStat: undefined,
        mpBegin: undefined,
        mpEndPlan: undefined,
        mpEndReal: undefined,
        mpPriority: undefined,
        mpQtyPlan: undefined,
        mpQtyReal: undefined,
        mpDelete: undefined,
        mpDesc: undefined,
        createBy: undefined,
        updateBy: undefined,
        createTime: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.mpId != null) {
            updateManufacturePlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.close();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 新增生产计划分配默认状态
            this.form.mpStat = '1'
            addManufacturePlan(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.close();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    // 选择所属订单的监听函数
    selectOrder(row) {
      const order = this.orderList.find(ele => ele.orCode === row)
      if (order) {
        const demands = this.orderDemandList.filter(ele => ele.orCode === order.orCode)
        this.productList = this.productListFull.filter(ele => demands.findIndex(demand => demand.maCode === ele.maCode) !== -1)
        this.form.maCode = undefined
        this.form.procCode = undefined
        this.form.mpQtyPlan = undefined
      } else {
        this.productList = []
      }
    },
    // 选择产品的监听函数
    selectProduct(row) {
      const order = this.orderList.find(ele => ele.orCode === this.form.orCode)
      const product = this.productListFull.find(ele => ele.maCode === row)
      if (product) {
        const demand = this.orderDemandList.find(ele => ele.orCode === order.orCode && ele.maCode === product.maCode)
        this.processList = this.processListFull.filter(ele => ele.maCode === product.maCode && ele.procStat === "5")
        this.form.mpQtyPlan = demand.odDemand
        this.form.procCode = undefined
      } else {
        this.processList = []
      }
    },
    // 关闭本页，返回到生产计划管理
    close() {
      const obj = { path: "/manufacture/manufacturePlan" };
      this.$tab.closeOpenPage(obj);
    },
  }
};
</script>
<style scoped>
.el-select {
  width: 100%;
}

.el-date-editor {
  width: 100%;
}
</style>