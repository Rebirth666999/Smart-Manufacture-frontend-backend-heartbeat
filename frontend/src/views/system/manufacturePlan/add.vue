<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <div class="card-header">
          <div>生产计划信息</div>
          <div>
            <el-button
              :loading="buttonLoading"
              type="primary"
              @click="submitForm"
            >保 存</el-button>
            <el-button
              :loading="buttonLoading"
              @click="resetPage"
            >重 置</el-button>
          </div>
        </div>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-col :span="12">
          <el-form-item label="所属订单" prop="orCode">
            <el-select v-model="form.orCode" placeholder="请选择订单" @change="selectOrder">
              <el-option v-for="item in orderList" :key="item.orCode" :label="item.orName" :value="item.orCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="prCode">
            <span slot="label">
              <el-tooltip placement="top">
                <div slot="content">
                  <div>须先选择订单，才能够加载订单的产品需求</div>
                </div>
                <i class="el-icon-question"></i>
              </el-tooltip>
              产品
            </span>
            <el-select v-model="form.prCode" placeholder="请选择产品" @change="selectProduct">
              <el-option v-for="item in productList" :key="item.prCode" :label="item.prName" :value="item.prCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最晚结束时间" prop="mpEndPlan">
            <el-date-picker clearable v-model="form.mpEndPlan" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择最晚结束时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="优先级" prop="mpPriority">
            <el-input v-model="form.mpPriority" placeholder="请输入优先级" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="计划产品数量" prop="mpQtyPlan">
            <el-input v-model="form.mpQtyPlan" placeholder="请输入计划产品数量" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="描述" prop="mpDesc">
            <el-input v-model="form.mpDesc" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
      </el-form>
    </el-card>
    <el-card class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>生产任务信息</div>
        </div>
      </div>
      <manufacture-task v-if='form.mpCode' :mpCode="form.mpCode" />
      <el-empty v-else description="保存生产计划后即可管理下属生产任务" />
    </el-card>
  </div>
</template>

<script>
import { listManufacturePlan, getManufacturePlan, delManufacturePlan, addManufacturePlan, updateManufacturePlan } from "@/api/system/manufacturePlan";
import { listOrder } from "@/api/system/order";
import { listProduct } from "@/api/system/product";
import { listOrderDemand } from "@/api/system/orderDemand";
import manufactureTask from '@/views/system/manufactureTask';

export default {
  name: "ManufacturePlan",
  components: {
    manufactureTask
  },
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
    await this.getOrderList();
    await this.getProductList();
    await this.getOrderDemandList();
    this.reset();
    if (this.$route.query.mpId) {
      this.processList = this.processListFull
      getManufacturePlan(this.$route.query.mpId).then(response => {
        this.form = response.data;
        this.selectOrder(this.form.orCode, 0)
        this.loading = false;
      });
    } else {
      this.loading = false;
    }
  },
  async activated() {
    this.loading = true;
    await this.getOrderList();
    await this.getProductList();
    await this.getOrderDemandList();
    this.reset();
    if (this.$route.query.mpId) {
      this.processList = this.processListFull
      getManufacturePlan(this.$route.query.mpId).then(response => {
        this.form = response.data;
        this.selectOrder(this.form.orCode, 0)
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
        listProduct().then(response => {
          this.productListFull = response.rows
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
        prCode: undefined,
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
    // 重置页面
    resetPage() {
      this.$router.replace(`/manufacture/manufacturePlan/add`)
      this.reset()
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.mpId != null) {
            updateManufacturePlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 新增生产计划分配默认状态
            this.form.mpStat = '1'
            addManufacturePlan(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$router.replace(`/manufacture/manufacturePlan/add?mpId=${response.data.mpId}`)
              this.form = response.data
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    // 选择所属订单的监听函数
    selectOrder(row, flag=1) {
      const order = this.orderList.find(ele => ele.orCode === row)
      if (order) {
        const demands = this.orderDemandList.filter(ele => ele.orCode === order.orCode)
        this.productList = this.productListFull.filter(ele => demands.findIndex(demand => demand.prCode === ele.prCode) !== -1)
        if (flag) {
          // flag=1用户主动选择
          // flag=0系统自动更新
          this.form.prCode = undefined
          this.form.procCode = undefined
          this.form.mpQtyPlan = undefined
        }
      } else {
        this.productList = []
      }
    },
    // 选择产品的监听函数
    selectProduct(row) {
      const order = this.orderList.find(ele => ele.orCode === this.form.orCode)
      const product = this.productListFull.find(ele => ele.prCode === row)
      if (product) {
        const demand = this.orderDemandList.find(ele => ele.orCode === order.orCode && ele.prCode === product.prCode)
        this.processList = this.processListFull.filter(ele => ele.prCode === product.prCode && ele.procStat === "5")
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

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 17px;
}

.controlled-card {
  margin-top: 10px;
}
</style>