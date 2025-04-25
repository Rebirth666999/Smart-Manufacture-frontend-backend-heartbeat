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
            <el-form-item label="所属订单" prop="orCode">
              <el-select v-model="form.orCode" placeholder="请选择订单" disabled>
                <el-option v-for="item in orderList" :key="item.orCode" :label="item.orCode" :value="item.orCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最晚结束时间" prop="mpmEndPlan">
              <el-date-picker clearable v-model="form.mpmEndPlan" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择最晚结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-form>
      </div>
      <el-empty v-else description="选择订单后即可录入生产计划" />
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>生产计划详细信息</div>
        </div>
      </div>
      <div v-if="$route.query.mpmId">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <!-- <el-form-item label="状态" prop="mpStat">
            <el-select v-model="queryParams.mpStat" placeholder="请选择状态" clearable>
              <el-option
                v-for="dict in dict.type.ices_manufacture_plan_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item> -->
          <el-form-item label="优先级" prop="mpPriority">
            <el-input
              v-model="queryParams.mpPriority"
              placeholder="请输入优先级"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!-- <el-form-item label="已删除" prop="mpDelete">
            <el-input
              v-model="queryParams.mpDelete"
              placeholder="请输入已删除"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item> -->
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:manufacturePlan:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:manufacturePlan:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:manufacturePlan:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:manufacturePlan:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table
          v-loading="loading"
          :data="manufacturePlanList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="计划详情ID" align="center" prop="mpId" v-if="true"/>
          <el-table-column label="计划详情编码" align="center" prop="mpCode" />
          <el-table-column label="所属生产计划" align="center" prop="mpmCode" />
          <el-table-column label="产品" align="center" prop="odCode">
            <template slot-scope="scope">
              {{ parseOdCode(scope.row.odCode) }}
            </template>
          </el-table-column>
          <!-- <el-table-column label="状态" align="center" prop="mpStat">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.ices_manufacture_plan_status" :value="scope.row.mpStat"/>
            </template>
          </el-table-column> -->
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
          <!-- <el-table-column label="已删除" align="center" prop="mpDelete" /> -->
          <!-- <el-table-column label="描述" align="center" prop="mpDesc" /> -->
          <el-table-column label="创建人" align="center" prop="mpCman" />
          <el-table-column label="创建时间" align="center" prop="mpCdate" />
          <el-table-column label="下发人" align="center" prop="mpRman" />
          <el-table-column label="下发时间" align="center" prop="mpRdate" />
          <el-table-column label="修改人" align="center" prop="mpMman" />
          <el-table-column label="修改时间" align="center" prop="mpMdate" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-show="scope.row.mpStat==='1' || scope.row.mpStat==='4' || scope.row.mpStat==='5'"
                v-hasPermi="['system:manufacturePlan:edit']"
              >修改</el-button>
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-finished"
                v-show="scope.row.mpStat==='1'"
                @click="handleSubmitReview(scope.row)"
              >提交审核</el-button> -->
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-refresh-left"
                v-show="scope.row.mpStat === '2'"
                @click="handleWithdrawReview(scope.row)"
              >撤回审核</el-button> -->
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                v-show="scope.row.mpStat==='4' || scope.row.mpStat==='5'"
                @click="handleDeprecated(scope.row)"
              >弃用</el-button> -->
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:manufacturePlan:remove']"
                v-show="scope.row.mpStat === '1'"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
      <el-empty v-else description="保存生产计划后即可查看生产计划详情" />
    </el-card>
  </div>
</template>

<script>
import { listManufacturePlan, getManufacturePlan, delManufacturePlan, addManufacturePlan, updateManufacturePlan } from "@/api/system/manufacturePlan";
import { listManufacturePlanMain, getManufacturePlanMain, delManufacturePlanMain, addManufacturePlanMain, updateManufacturePlanMain } from "@/api/system/manufacturePlanMain";
import { listOrder } from "@/api/system/order";
import { listProduct } from "@/api/system/product";
import { listOrderDemand } from "@/api/system/orderDemand";

export default {
  name: "ManufacturePlanMain",
  dicts: ['ices_manufacture_plan_status'],
  data() {
    return {
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
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
        mpmId: [
          { required: true, message: "生产计划不能为空", trigger: "blur" }
        ],
        orCode: [
          { required: true, message: "所属订单不能为空", trigger: "blur" }
        ],
        mpmEndPlan: [
          { required: true, message: "最晚结束时间不能为空", trigger: "blur" }
        ]
      },
      // 生产计划列表
      manufacturePlanList: [],
      // 订单列表
      orderList: [],
      // 全部产品列表
      productList: [],
      // 订单产品需求
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
    if (this.$route.query.mpmId) {
      getManufacturePlanMain(this.$route.query.mpmId).then(response => {
        this.form = response.data
        this.queryParams.orCode = this.form.orCode
        this.getList()
      });
    } else if (this.$route.query.orCode) {
      this.queryParams.orCode = this.$route.query.orCode
      this.form.orCode = this.$route.query.orCode
      this.$router.replace("/manufacture/manufacturePlan/addMain")
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
    if (this.$route.query.mpmId) {
      getManufacturePlanMain(this.$route.query.mpmId).then(response => {
        this.form = response.data
        this.queryParams.orCode = this.form.orCode
        this.getList()
      });
    } else if (this.$route.query.orCode) {
      this.queryParams.orCode = this.$route.query.orCode
      this.form.orCode = this.$route.query.orCode
      this.$router.replace("/manufacture/manufacturePlan/addMain")
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
          this.orderDemandList = []
          response.rows.forEach(demand => {
            // 追加产品名称字段
            this.orderDemandList.push({
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
    /** 查询生产计划详情列表 */
    getList() {
      this.loading = true;
      listManufacturePlan(this.queryParams).then(response => {
        this.manufacturePlanList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.extId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置搜索按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 取消按钮
    cancel() {
      this.close();
    },
    // 表单重置
    reset() {
      this.form = {
        mpmId: undefined,
        orCode: undefined,
        mpmStat: undefined,
        mpmBegin: undefined,
        mpmEndPlan: undefined,
        mpmEndReal: undefined,
        mpmQtyPlan: undefined,
        mpmQtyReal: undefined,
        mpmDelete: undefined,
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
     * @date 20250425
     */
    resetOrderQuery() {
      this.resetForm("queryOrderForm");
      this.handleQuery();
    },
    // 重置页面
    resetPage() {
      this.$router.replace(`/manufacture/manufacturePlan/addMain`)
      this.reset()
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.mpmId != null) {
            updateManufacturePlanMain(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            // 新增生产计划分配默认状态
            this.form.mpmStat = '1'
            addManufacturePlanMain(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$router.replace(`/manufacture/manufacturePlan/addMain?mpmId=${response.data.mpmId}`);
              this.getList();
              this.form = response.data
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push(`/manufacture/manufacturePlan/add`)
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const mpId = row.mpId || this.ids
      this.$router.push(`/manufacture/manufacturePlan/add?mpId=${mpId}`)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const mpIds = row.mpId || this.ids;
      this.$modal.confirm('是否确认删除生产计划编号为"' + mpIds + '"的数据项？').then(() => {
        this.loading = true;
        return delManufacturePlan(mpIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/manufacturePlan/export', {
        ...this.queryParams
      }, `manufacturePlan_${new Date().getTime()}.xlsx`)
    },
    // 选择所属订单的监听函数
    selectOrder(row) {
      this.form.orCode = this.queryParams.orCode
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