<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选生产计划`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属订单" prop="orCode">
        <el-select
          v-model="queryParams.orCode"
          placeholder="请选择订单"
          clearable
          :disabled="mode === 1"
        >
          <el-option
            v-for="item in orderList"
            :key="item.orCode"
            :label="item.orName"
            :value="item.orCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工艺流程" prop="procCode">
        <el-select
          v-model="queryParams.procCode"
          placeholder="请选择工艺流程"
          clearable
          :disabled="mode === 2"
        >
          <el-option
            v-for="item in processListFull"
            :key="item.procCode"
            :label="item.procName"
            :value="item.procCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="mpStat">
        <el-select v-model="queryParams.mpStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_manufacture_plan_status_review"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" prop="mpPriority">
        <el-input
          v-model="queryParams.mpPriority"
          placeholder="请输入优先级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
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

    <el-table v-loading="loading" :data="manufacturePlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="生产计划ID" align="center" prop="mpCode" v-if="true"/>
      <el-table-column label="所属订单" align="center" prop="orCode">
        <template slot-scope="scope">
          {{ getOrderName(scope.row.orCode) }}
        </template>
      </el-table-column>
      <el-table-column label="工艺流程" align="center" prop="procCode">
        <template slot-scope="scope">
          {{ getProcessName(scope.row.procCode) }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="mpStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_manufacture_plan_status_review" :value="scope.row.mpStat"/>
        </template>
      </el-table-column>
      <el-table-column label="实际开始时间" align="center" prop="mpBegin" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mpBegin, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最晚结束时间" align="center" prop="mpEndPlan" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mpEndPlan, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际结束时间" align="center" prop="mpEndReal" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mpEndReal, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="mpPriority" />
      <el-table-column label="计划产品数量" align="center" prop="mpQtyPlan" />
      <el-table-column label="已完成产品数量" align="center" prop="mpQtyReal" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="mpDesc" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >查看详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.mpStat === '2' || scope.row.mpStat === '7' || scope.row.mpStat === 'a'"
            @click="startReview(scope.row)"
          >开始审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            v-show="scope.row.mpStat === '3' || scope.row.mpStat === '8' || scope.row.mpStat === 'b'"
            @click="passReview(scope.row)"
          >通过审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            v-show="scope.row.mpStat === '3' || scope.row.mpStat === '8' || scope.row.mpStat === 'b'"
            @click="rejectReview(scope.row)"
          >驳回审核</el-button>
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

    <!-- 查看生产计划详情对话框 -->
    <el-dialog :title="`查看生产计划详情 - ID: ${viewData.mpCode}`" :visible.sync="viewOpen" width="650px" append-to-body>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="生产计划ID">{{ viewData.mpCode }}</el-descriptions-item>
        <el-descriptions-item label="所属订单">
          {{ getOrderName(viewData.orCode) }}
        </el-descriptions-item>
        <el-descriptions-item label="工艺流程">
          {{ getProcessName(viewData.procCode) }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="dict.type.ices_manufacture_plan_status_review" :value="viewData.mpStat"/>
        </el-descriptions-item>
        <el-descriptions-item label="实际开始时间">
          {{ parseTime(viewData.mpBegin, '{y}-{m}-{d}') }}
        </el-descriptions-item>
        <el-descriptions-item label="最晚结束时间">
          {{ parseTime(viewData.mpEndPlan, '{y}-{m}-{d}') }}
        </el-descriptions-item>
        <el-descriptions-item label="实际结束时间">
          {{ parseTime(viewData.mpEndReal, '{y}-{m}-{d}') }}
        </el-descriptions-item>
        <el-descriptions-item label="优先级">{{ viewData.mpPriority }}</el-descriptions-item>
        <el-descriptions-item label="计划产品数量">{{ viewData.mpQtyPlan }}</el-descriptions-item>
        <el-descriptions-item label="已完成产品数量">{{ viewData.mpQtyReal }}</el-descriptions-item>
        <el-descriptions-item label="描述">{{ viewData.mpDesc }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ parseTime(viewData.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ parseTime(viewData.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import { getManufacturePlan, listReviewManufacturePlan, updateManufacturePlan } from "@/api/system/manufacturePlan";
import { listProcess } from "@/api/system/process";
import { listOrder } from "@/api/system/order";

export default {
  name: "ManufacturePlanReview",
  dicts: ['ices_manufacture_plan_status_review'],
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
      // 所有工艺流程
      processListFull: [],
      // 订单列表
      orderList: [],
      // 查看详情相关
      viewOpen: false,
      viewData: {},
      // 1-根据订单管理 2-根据工艺流程管理
      mode: 0,
      // 页面顶部提示
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.orCode) {
      this.mode = 1
    } else if (this.$route.query.procCode) {
      this.mode = 2
    }
    await this.getProcessList();
    await this.getOrderList();
    this.getList();
  },
  async activated() {
    if (this.$route.query.orCode) {
      this.mode = 1
    } else if (this.$route.query.procCode) {
      this.mode = 2
    } else {
      this.mode = 0
    }
    await this.getProcessList();
    await this.getOrderList();
    this.getList();
  },
  methods: {
    // 获取订单名称
    getOrderName(orCode) {
      if (!orCode) return '';
      if (!this.orderList || this.orderList.length === 0) return orCode;
      
      const order = this.orderList.find(ele => ele.orCode === orCode);
      return order ? order.orName : orCode;
    },
    
    // 获取工艺流程名称
    getProcessName(procCode) {
      if (!procCode) return '';
      if (!this.processListFull || this.processListFull.length === 0) return procCode;
      
      const proc = this.processListFull.find(ele => ele.procCode === procCode);
      return proc ? proc.procName : procCode;
    },
    
    // 开始审核
    startReview(row) {
      this.$modal.confirm('是否要开始审核？').then(() => {
        this.loading = true;
        getManufacturePlan(row.mpId).then(response => {
          this.form = response.data;
          if (this.form.mpStat === '2') this.form.mpStat = '3';
          else if (this.form.mpStat === '7') this.form.mpStat = '8';
          else this.form.mpStat = 'b';
          updateManufacturePlan(this.form).then(response => {
            this.$modal.msgSuccess("已开始审核");
            this.getList();
          });
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    
    // 通过审核
    passReview(row) {
      this.$modal.confirm('是否要通过审核？').then(() => {
        this.loading = true;
        getManufacturePlan(row.mpId).then(response => {
          this.form = response.data;
          if (this.form.mpStat === '3') this.form.mpStat = '4';
          else if (this.form.mpStat === '8') this.form.mpStat = '6';
          else this.form.mpStat = '9';
          updateManufacturePlan(this.form).then(response => {
            this.$modal.msgSuccess("已通过审核");
            this.getList();
          });
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    
    // 驳回审核
    rejectReview(row) {
      this.$modal.confirm('是否要驳回审核？').then(() => {
        this.loading = true;
        getManufacturePlan(row.mpId).then(response => {
          this.form = response.data;
          if (this.form.mpStat === '3') this.form.mpStat = '1';
          else if (this.form.mpStat === '8' || this.form.mpStat === 'b') this.form.mpStat = '4';
          updateManufacturePlan(this.form).then(response => {
            this.$modal.msgSuccess("已驳回审核");
            this.getList();
          });
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },

    // 查询工艺流程列表
    getProcessList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listProcess().then(response => {
          this.processListFull = response.rows || []
          if (this.mode === 2) {
            let proc = this.processListFull.find(ele => ele.procCode === this.$route.query.procCode)
            // 构造提示文本
            this.hint = "工艺流程 "
            this.hint += proc.procName
            this.hint += " "
            // 设置筛选
            this.queryParams.procCode = proc.procCode
            this.queryParams.orCode = undefined
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false;
        })
      })
    },
    // 查询订单列表
    getOrderList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listOrder().then(response => {
          this.orderList = response.rows || []
          if (this.mode === 1) {
            let order = this.orderList.find(ele => ele.orCode === this.$route.query.orCode)
            // 构造提示文本
            this.hint = "订单 "
            this.hint += order.orName
            this.hint += " "
            // 设置筛选
            this.queryParams.orCode = order.orCode
            this.queryParams.procCode = undefined
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false;
        })
      })
    },
    /** 查询生产计划列表 */
    getList() {
      this.loading = true;
      listReviewManufacturePlan(this.queryParams).then(response => {
        this.manufacturePlanList = response.rows || [];
        this.total = response.total || 0;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.orCode = this.$route.query.orCode
      this.queryParams.procCode = this.$route.query.procCode
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.mpId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/manufacturePlan/export', {
        ...this.queryParams
      }, `manufacturePlan_${new Date().getTime()}.xlsx`)
    },
    /** 查看详情按钮操作 */
    handleView(row) {
      this.loading = true;
      getManufacturePlan(row.mpId).then(response => {
        this.loading = false;
        this.viewData = response.data || {};
        this.viewOpen = true;
      });
    }
  }
};
</script> 