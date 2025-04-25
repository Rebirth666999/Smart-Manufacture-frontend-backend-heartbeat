<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>订单信息</div>
        </div>
      </div>
      <el-form :model="queryParams" ref="queryOrderForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="所属订单" prop="orCode">
          <el-select
            v-model="queryParams.orCode"
            placeholder="请选择订单"
            clearable
            filterable
            @change="selectOrder"
          >
            <el-option
              v-for="item in orderList"
              :key="item.orCode"
              :label="item.orCode"
              :value="item.orCode"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <!-- <el-button type="primary" icon="el-icon-search" size="mini" @click="selectOrder">搜索</el-button> -->
          <el-button icon="el-icon-refresh" size="mini" @click="resetOrderQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <div v-if="queryParams.orCode">
        <el-table
          :data="orderList.filter(ele => ele.orCode === queryParams.orCode)"
        >
          <el-table-column label="订单ID" align="center" prop="orId" v-if="true"/>
          <el-table-column label="订单编码" align="center" prop="orCode" />
          <el-table-column label="订单优先级" align="center" prop="orPriority" />
          <el-table-column label="截止时间" align="center" prop="orDeadline" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.orDeadline, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="总价" align="center" prop="orPrice" />
          <el-table-column label="创建人" align="center" prop="orCman" />
          <el-table-column label="创建时间" align="center" prop="orCdate" />
          <el-table-column label="修改人" align="center" prop="orMman" />
          <el-table-column label="修改时间" align="center" prop="orMdate" />
        </el-table>
      </div>
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>生产计划信息</div>
        </div>
      </div>
      <div v-if="queryParams.orCode">
        <el-table
          v-loading="loading"
          :data="manufacturePlanMainList"
        >
          <el-table-column label="计划ID" align="center" prop="mpmId" v-if="true"/>
          <el-table-column label="计划编码" align="center" prop="mpmCode" />
          <el-table-column label="订单" align="center" prop="orCode" />
          <el-table-column label="实际开始时间" align="center" prop="mpmBegin">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpmBegin, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="最晚结束时间" align="center" prop="mpmEndPlan">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpmEndPlan, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="实际结束时间" align="center" prop="mpmEndReal">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpmEndReal, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建人" align="center" prop="mpmCman" />
          <el-table-column label="创建时间" align="center" prop="mpmCdate" />
          <el-table-column label="修改人" align="center" prop="mpmMman" />
          <el-table-column label="修改时间" align="center" prop="mpmMdate" />
        </el-table>
      </div>
      <el-empty v-else description="选择订单后即可查看生产计划" />
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>已有生产计划详细信息</div>
        </div>
      </div>
      <div v-if="queryParams.orCode">
        <el-table
          v-loading="loading"
          :data="manufacturePlanList"
          v-if="queryParams.orCode"
        >
          <el-table-column label="计划详情ID" align="center" prop="mpId" v-if="true"/>
          <el-table-column label="计划详情编码" align="center" prop="mpCode" />
          <el-table-column label="所属生产计划" align="center" prop="mpmCode" />
          <el-table-column label="所属订单" align="center" prop="orCode" />
          <el-table-column label="产品" align="center" prop="odCode">
            <template slot-scope="scope">
              {{ parseOdCode(scope.row.odCode) }}
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="mpStat">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.ices_manufacture_plan_status" :value="scope.row.  mpStat"/>
            </template>
          </el-table-column>
          <el-table-column label="实际开始时间" align="center" prop="mpBegin">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpBegin, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="最晚结束时间" align="center" prop="mpEndPlan">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpEndPlan, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="实际结束时间" align="center" prop="mpEndReal">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.mpEndReal, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="优先级" align="center" prop="mpPriority" />
          <el-table-column label="计划产品数量" align="center" prop="mpQtyPlan" />
          <el-table-column label="已完成产品数量" align="center" prop="mpQtyReal" />
          <el-table-column label="创建人" align="center" prop="mpCman" />
          <el-table-column label="创建时间" align="center" prop="mpCdate" />
          <el-table-column label="下发人" align="center" prop="mpRman" />
          <el-table-column label="下发时间" align="center" prop="mpRdate" />
          <el-table-column label="修改人" align="center" prop="mpMman" />
          <el-table-column label="修改时间" align="center" prop="mpMdate" />
        </el-table>
        <pagination
          v-show="total>0 && queryParams.orCode"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
      <el-empty v-else description="选定订单后即可查看生产计划详细信息" />
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>维护生产计划详细信息</div>
          <div v-show="queryParams.orCode">
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
      <div v-if="queryParams.orCode">
        <el-form ref="form" :model="form" :rules="rules" label-width="110px">
          <el-col :span="12">
            <el-form-item label="详细信息编码" prop="mpCode">
              <el-input v-model="form.mpCode" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属订单" prop="orCode">
              <el-select v-model="form.orCode" placeholder="请选择订单" disabled>
                <el-option v-for="item in orderList" :key="item.orCode" :label="item.orCode" :value="item.orCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属生产计划" prop="mpmCode">
              <el-input v-model="form.mpmCode" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="odCode">
              <span slot="label">
                <el-tooltip placement="top">
                  <div slot="content">
                    <div>须先选择订单，才能够加载订单的产品需求</div>
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
                产品
              </span>
              <el-select v-model="form.odCode" placeholder="请选择产品" @change="selectProduct">
                <el-option v-for="item in orderDemandList" :key="item.odCode" :label="item.prName" :value="item.odCode">
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
      </div>
      <el-empty v-else description="选定订单后即可录入生产计划详细信息" />
    </el-card>
    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>生产任务信息</div>
        </div>
      </div>
      <manufacture-task v-if='form.mpCode' :mpCode="form.mpCode" />
      <el-empty v-else description="保存生产计划详细后即可管理下属生产任务" />
    </el-card>
  </div>
</template>

<script>
import { listManufacturePlan, getManufacturePlan, delManufacturePlan, addManufacturePlan, updateManufacturePlan } from "@/api/system/manufacturePlan";
import { listManufacturePlanMain } from "@/api/system/manufacturePlanMain";
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
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orCode: undefined,
        mpStat: undefined,
        mpPriority: undefined,
        mpDelete: 0,
      },
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
        mpmCode: [
          { required: true, message: "所属生产计划不能为空", trigger: "blur" }
        ],
        odCode: [
          { required: true, message: "产品不能为空", trigger: "blur" }
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
      // 生产计划列表
      manufacturePlanList: [],
      // 订单列表
      orderList: [],
      // 全部产品列表
      productList: [],
      // 所有订单的产品需求
      orderDemandListFull: [],
      // 选择订单的产品需求
      orderDemandList: [],
      // 生产计划信息
      manufacturePlanMainList: []
    };
  },
  async created() {
    this.loading = true;
    await this.getOrderList();
    await this.getProductList();
    await this.getOrderDemandList();
    this.reset();
    if (this.$route.query.mpId) {
      getManufacturePlan(this.$route.query.mpId).then(response => {
        this.form = response.data
        this.queryParams.orCode = this.form.orCode
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
      getManufacturePlan(this.$route.query.mpId).then(response => {
        this.form = response.data
        this.queryParams.orCode = this.form.orCode
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
          this.orderDemandListFull = []
          response.rows.forEach(demand => {
            // 追加产品名称字段
            this.orderDemandListFull.push({
              ...demand,
              prName: this.productList.find(ele => ele.prCode === demand.prCode).prName
            })
          })
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
          this.productList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 查询订单列表
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
    // 查询生产计划主表列表
    getMainList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufacturePlanMain({ orCode: this.queryParams.orCode }).then(response => {
          this.manufacturePlanMainList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询生产计划列表 */
    getList() {
      this.loading = true;
      listManufacturePlan(this.queryParams).then(response => {
        this.manufacturePlanList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        odCode: undefined,
        mpmCode: undefined,
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
      if (this.queryParams.orCode) {
        this.form.orCode = this.queryParams.orCode
      }
    },
    /** 
     * 计划信息重置按钮
     * 只能重置订单筛选项
     * @author YangZY
     * @date 20250424
     */
    resetOrderQuery() {
      this.resetForm("queryOrderForm");
      this.idSelect = undefined
      this.codeSelect = undefined
      this.handleQuery();
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
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 新增生产计划分配默认状态
            this.form.mpStat = '1'
            addManufacturePlan(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$router.replace(`/manufacture/manufacturePlan/add?mpId=${response.data.mpId}`);
              this.getList();
              this.form = response.data
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    // 选择所属订单的监听函数
    async selectOrder(row, flag=1) {
      await this.getMainList()
      this.queryParams.pageNum = 1
      this.form.orCode = this.queryParams.orCode
      console.log(this.manufacturePlanMainList)
      this.form.mpmCode = this.manufacturePlanMainList[0].mpmCode
      this.getList()
      const order = this.orderList.find(ele => ele.orCode === row)
      if (order) {
        this.orderDemandList = this.orderDemandListFull.filter(ele => ele.orCode === order.orCode)
        if (flag) {
          // flag=1用户主动选择
          // flag=0系统自动更新
          this.form.odCode = undefined
          this.form.mpQtyPlan = undefined
        }
      } else {
        this.orderDemandList = []
      }
    },
    /**
     * 选择产品的监听函数
     * 自动填入计划数量
     * @param {string} odCode 选择的odCode
     * @author YangZY
     * @date 20250423
     */
    selectProduct(odCode) {
      const demand = this.orderDemandListFull.find(ele => ele.odCode === odCode)
      if (demand) {
        this.form.mpQtyPlan = demand.odDemand
      }
    },
    // 关闭本页，返回到生产计划管理
    close() {
      const obj = { path: "/manufacture/manufacturePlan" };
      this.$tab.closeOpenPage(obj);
    },
    /**
     * 解析产品需求字段
     * @author YangZY
     * @date 20250423
     */
    parseOdCode(odCode) {
      const demand = this.orderDemandList.find(ele => ele.odCode === odCode)
      if (demand) {
        return this.productList.find(ele => ele.prCode === demand.prCode).prName || ''
      }
      return ''
    }
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