<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>生产计划信息</div>
        </div>
      </div>
      <div>
        <el-form :model="queryParams" ref="queryOrderForm" size="small" :inline="true"  label-width="68px">
          <el-form-item label="订单" prop="orCode">
            <el-button size="mini" type="primary" @click="openSelectOrder">
              {{ currentOrCode || '选择订单' }}
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-refresh" size="mini" @click="resetOrderQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              :disabled="manufacturePlanMainList.length > 0"
              @click="handleAddMain"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExportMain"
            >导出</el-button>
          </el-col>
        </el-row>
  
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
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdateMain(scope.row)"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDeleteMain(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>生产计划详细信息</div>
        </div>
      </div>
      <div v-if="queryParams.orCode && manufacturePlanMainList.length > 0">
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
          @current-change="handleCurrentChange"
          highlight-current-row
        >
          <el-table-column label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio :value="scope.row.mpId === idSelect" :label="true" />
            </template>
          </el-table-column>
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
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-show="scope.row.mpStat==='1' || scope.row.mpStat==='4' || scope.row.mpStat==='5'"
                v-hasPermi="['system:manufacturePlan:edit']"
              >修改</el-button> -->
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
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:manufacturePlan:remove']"
                v-show="scope.row.mpStat === '1'"
              >删除</el-button> -->

              
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:manufacturePlan:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:manufacturePlan:remove']"
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
      <el-empty v-else description="暂无生产计划详细信息" />
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <el-tabs value="task">
        <el-tab-pane label="生产任务" name="task">
          <manufacture-task v-if='idSelect' :key="idSelect" :mpCode="codeSelect" @update="getList" />
          <el-empty v-else description="选中生产计划后即可管理下属生产任务" />
        </el-tab-pane>
        <el-tab-pane label="生产材料" name="material">
          <manufacture-material v-if='idSelect' :key="idSelect" :mpCode="codeSelect" />
          <el-empty v-else description="选中生产计划后即可管理生产材料" />
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <!-- 选择订单对话框 -->
    <el-dialog title="选择订单" :visible.sync="open" width="550px" append-to-body>
      <el-form label-width="110px">
        <el-form-item label="订单编号" prop="orCode">
          <el-select
            v-model="queryParams.orCode"
            placeholder="请选择订单"
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
      </el-form>
      <el-descriptions border :column="1">
        <el-descriptions-item label="订单ID">
          {{ currentOrder ? currentOrder.orId : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="订单编码">
          {{ currentOrder ? currentOrder.orCode : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="订单优先级">
          {{ currentOrder ? currentOrder.orPriority : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="截止时间">
          {{ currentOrder ? parseTime(currentOrder.orDeadline, '{y}-{m}-{d}') : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="总价">
          {{ currentOrder ? currentOrder.orPrice : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="创建人">
          {{ currentOrder ? currentOrder.orCman : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ currentOrder ? currentOrder.orCdate : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="修改人">
          {{ currentOrder ? currentOrder.orMman : '' }}
        </el-descriptions-item>
        <el-descriptions-item label="修改时间">
          {{ currentOrder ? currentOrder.orMdate : '' }}
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitOrder">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listManufacturePlan, getManufacturePlan, delManufacturePlan, addManufacturePlan, updateManufacturePlan } from "@/api/system/manufacturePlan";
import { listManufacturePlanMain, getManufacturePlanMain, delManufacturePlanMain, addManufacturePlanMain, updateManufacturePlanMain } from "@/api/system/manufacturePlanMain";
import { listOrder } from "@/api/system/order";
import { listProduct } from "@/api/system/product";
import { listOrderDemand } from "@/api/system/orderDemand";
import manufactureTask from '@/views/system/manufactureTask';
import ManufactureMaterial from '@/views/system/manufactureTask/material';

export default {
  name: "ManufacturePlan",
  components: {
    manufactureTask,
    ManufactureMaterial
  },
  dicts: ['ices_manufacture_plan_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中内容
      idSelect: undefined,
      // 选中code
      codeSelect: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 生产计划表格数据
      manufacturePlanList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orCode: undefined,
        procCode: undefined,
        mpStat: undefined,
        mpPriority: undefined,
        mpDelete: 0,
      },
      // 用于编辑属性的表单
      form: {},
      // 选择订单窗口是否打开
      open: false,
      // 用于显示的当前选择订单
      currentOrCode: '',
      // 当前选择订单信息
      currentOrder: undefined,
      // 订单列表
      orderList: [],
      // 产品列表
      productList: [],
      // 产品需求列表
      orderDemandList: [],
      // 生产计划主表
      manufacturePlanMainList: []
    };
  },
  async created() {
    await this.getProductList();
    await this.getOrderList();
    await this.getOrderDemandList();
    await this.getList();
    if (this.$route.query.mpCode) {
      const manufacturePlan = this.manufacturePlanList.find(ele => ele.mpCode === this.$route.query.mpCode)
      this.$router.replace('/manufacture/manufacturePlan')
      if (manufacturePlan) {
        this.queryParams.orCode = manufacturePlan.orCode
        this.handleQuery()
        this.idSelect = manufacturePlan.mpId
        this.codeSelect = manufacturePlan.mpCode
      }
    }
  },
  async activated() {
    await this.getProductList();
    await this.getOrderList();
    await this.getOrderDemandList();
    await this.getList();
    if (this.$route.query.mpCode) {
      const manufacturePlan = this.manufacturePlanList.find(ele => ele.mpCode === this.$route.query.mpCode)
      this.$router.replace('/manufacture/manufacturePlan')
      if (manufacturePlan) {
        this.queryParams.orCode = manufacturePlan.orCode
        this.handleQuery()
        this.idSelect = manufacturePlan.mpId
        this.codeSelect = manufacturePlan.mpCode
      }
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
      return new Promise((resolve, reject) => {
        this.loading = true;
        listManufacturePlan(this.queryParams).then(response => {
          this.manufacturePlanList = response.rows;
          this.total = response.total;
          this.loading = false;
          resolve()
        }).catch(() => {
          reject()
        })
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.idSelect = undefined
      this.codeSelect = undefined
      if (this.queryParams.orCode) {
        this.getList();
        this.getMainList();
      } else {
        this.manufacturePlanMainList = []
        this.manufacturePlanList = []
      }
    },
    /** 
     * 计划详细信息重置按钮
     * 只能重置订单以外的筛选项
     * @author YangZY
     * @date 20250423
     */
    resetQuery() {
      this.resetForm("queryForm");
      this.idSelect = undefined
      this.codeSelect = undefined
      this.handleQuery();
    },
    /** 
     * 计划信息重置按钮
     * 只能重置订单筛选项
     * @author YangZY
     * @date 20250423
     */
    resetOrderQuery() {
      this.resetForm("queryOrderForm");
      this.currentOrCode = ''
      this.idSelect = undefined
      this.codeSelect = undefined
      this.handleQuery();
    },
    // 选中数据条目
    handleCurrentChange(current, old) {
      if (current) {
        this.idSelect = current.mpId
        this.codeSelect = current.mpCode
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push(`/manufacture/manufacturePlan/add`)
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const mpId = row.mpId || this.idSelect
      this.$router.push(`/manufacture/manufacturePlan/add?mpId=${mpId}`)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const mpIds = row.mpId || this.idSelect;
      this.$modal.confirm('是否确认删除生产计划详情编号为"' + mpIds + '"的数据项？').then(() => {
        this.loading = true;
        return delManufacturePlan(mpIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.idSelect = undefined
        this.codeSelect = undefined
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
    // 提交审核
    handleSubmitReview(row) {
      const mpId = row.mpId;
      this.$modal.confirm('是否要提交审核？审核在开始之前可以撤回。').then(() => {
        this.loading = true;
        getManufacturePlan(mpId).then(response => {
          this.form = response.data;
          this.form.mpStat = "2";
          updateManufacturePlan(this.form).then(response => {
            this.$modal.msgSuccess("已提交审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    // 撤回审核
    handleWithdrawReview(row) {
      const mpId = row.mpId;
      this.$modal.confirm('是否要撤回审核？若审核已开始即无法撤回。').then(() => {
        this.loading = true;
        getManufacturePlan(mpId).then(response => {
          this.form = response.data;
          if(this.form.mpStat === '2') this.form.mpStat = '1';
          else if(this.form.mpStat === '7' || this.form.mpStat === 'a') this.form.mpStat = '4';
          updateManufacturePlan(this.form).then(response => {
            this.$modal.msgSuccess("已撤回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    //弃用
    handleDeprecated(row) {
      const mpId = row.mpId;
      this.$modal.confirm('是否确认弃用该生产计划？').then(() => {
        this.loading = true;
        getManufacturePlan(mpId).then(response => {
          this.form = response.data;
          this.form.mpStat = "a";
          updateManufacturePlan(this.form).then(response => {
            this.$modal.msgSuccess("已弃用");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 新增生产计划主表 */
    handleAddMain() {
      this.$router.push(`/manufacture/manufacturePlan/addMain?orCode=${this.queryParams.orCode}`)
    },
    /** 修改生产计划主表 */
    handleUpdateMain(row) {
      const mpmId = row.mpmId
      this.$router.push(`/manufacture/manufacturePlan/addMain?mpmId=${mpmId}`)
    },
    /** 删除生产计划主表 */
    handleDeleteMain(row) {
      const mpmIds = row.mpmId;
      this.$modal.confirm('是否确认删除生产计划编号为"' + mpmIds + '"的数据项？').then(() => {
        this.loading = true;
        return delManufacturePlanMain(mpmIds);
      }).then(() => {
        this.loading = false;
        this.manufacturePlanMainList = []
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出生产计划主表 */
    handleExportMain() {
      this.download('system/manufacturePlanMain/export', {
        orCode: this.queryParams.orCode
      }, `manufacturePlanMain_${new Date().getTime()}.xlsx`)
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
    },
    /**
     * 打开选择订单窗口
     * @author YangZY
     * @date 20250426
     */
    openSelectOrder() {
      this.open = true
      if (this.currentOrCode) {
        this.selectOrder(this.currentOrCode)
      } else {
        this.currentOrder = undefined
      }
    },
    /**
     * 生产计划选择订单
     * @author YangZY
     * @date 20250426
     */
    selectOrder(orCode) {
      this.currentOrder = this.orderList.find(ele => ele.orCode === orCode)
    },
    /**
     * 关闭选择订单窗口
     * @author YangZY
     * @date 20250426
     */
    cancel() {
      this.queryParams.orCode = this.currentOrCode
      this.open = false
    },
    /**
     * 确认选择订单
     * @author YangZY
     * @date 20250426
     */
    submitOrder() {
      this.currentOrCode = this.queryParams.orCode
      this.handleQuery()
      this.open = false
    }
  }
};
</script>
<style scoped>
.el-select {
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
::v-deep .el-radio span.el-radio__label {
  display: none;
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
::v-deep .el-tabs__item {
  font-size: 17px;
}
</style>
