<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选订单`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>
    <el-card class="view-card">
      <div slot="header">
        <div class="card-header">
          <div>订单审核信息</div>
        </div>
      </div>
      <div>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所需产品" prop="maCode">
        <el-select
          v-model="queryParams.maCode"
          placeholder="请选择产品"
          clearable
          :disabled="mode === 1"
        >
          <el-option
            v-for="item in productList"
            :key="item.maCode"
            :label="item.maName"
            :value="item.maCode"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="客户" prop="clCode">
        <el-input
          v-model="queryParams.clCode"
          placeholder="请输入客户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单名称" prop="orName">
        <el-input
          v-model="queryParams.orName"
          placeholder="请输入订单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="orStat">
        <el-select v-model="queryParams.orStat" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_order_status_review"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" prop="orPriority">
        <el-input
          v-model="queryParams.orPriority"
          placeholder="请输入订单优先级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="截止时间">
        <el-date-picker
          v-model="daterangeOrDeadline"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['00:00:00', '23:59:59']"
        ></el-date-picker>
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
          v-hasPermi="['system:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
          v-loading="loading"
          :data="orderList"
          @current-change="handleCurrentChange"
          highlight-current-row
          max-height="240"
        >
          <el-table-column label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio :value="scope.row.orId === idSelect" :label="true" />
            </template>
          </el-table-column> 
      <el-table-column label="订单ID" align="center" prop="orCode" v-if="true"/>
      <el-table-column label="所需产品" align="center" prop="maCode">
        <template slot-scope="scope">
          {{ getProductName(scope.row.maCode) }}
        </template>
      </el-table-column>
      <el-table-column label="客户" align="center" prop="clCode" />
      <el-table-column label="订单名称" align="center" prop="orName" />
      <el-table-column label="状态代码" align="center" prop="orStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_order_status_review" :value="scope.row.orStat"/>
        </template>
      </el-table-column>
      <el-table-column label="所需产品数量" align="center" prop="orDemand" />
      <el-table-column label="订单优先级" align="center" prop="orPriority" />
      <el-table-column label="截止时间" align="center" prop="orDeadline" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orDeadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="总价" align="center" prop="orPrice" />
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
      <el-table-column label="描述" align="center" prop="orDesc" />
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
            v-show="scope.row.orStat === '2' || scope.row.orStat === '8' || scope.row.orStat === 'b'"
            @click="startReview(scope.row)"
          >开始审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            v-show="scope.row.orStat === '3' || scope.row.orStat === '9' || scope.row.orStat === 'c'"
            @click="passReview(scope.row)"
          >通过审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            v-show="scope.row.orStat === '3' || scope.row.orStat === '9' || scope.row.orStat === 'c'"
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

    <!-- 查看订单详情对话框 -->
    <el-dialog :title="'查看订单详情 - ' + viewData.orCode" :visible.sync="viewOpen" width="530px" append-to-body>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="订单ID">{{ viewData.orId }}</el-descriptions-item>
        <el-descriptions-item label="订单编码">{{ viewData.orCode }}</el-descriptions-item>
        <el-descriptions-item label="客户">{{ viewData.clCode }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="dict.type.ices_order_status_review" :value="viewData.orStat"/>
        </el-descriptions-item>
        <el-descriptions-item label="订单优先级">{{ viewData.orPriority }}</el-descriptions-item>
        <el-descriptions-item label="截止时间">
          {{ parseTime(viewData.orDeadline, '{y}-{m}-{d}') }}
        </el-descriptions-item>
        <el-descriptions-item label="总价">{{ viewData.orPrice }}</el-descriptions-item>
        <el-descriptions-item label="描述">{{ viewData.orDesc }}</el-descriptions-item>
        <el-descriptions-item label="创建人">{{ viewData.orCman }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ viewData.orCdate }}</el-descriptions-item>
        <el-descriptions-item label="修改人">{{ viewData.orMman }}</el-descriptions-item>
        <el-descriptions-item label="修改时间">{{ viewData.orMdate }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
    </div>
    </el-card>
    <el-card class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>订单产品信息</div>
        </div>
      </div>
      <order-demand v-if='idSelect' :key="idSelect" :orCode="codeSelect" />
      <el-empty v-else description="选中订单后即可管理订单产品" />
    </el-card>
  </div>
</template>

<script>
import { getOrder, listReviewOrder, updateOrder, listOrder } from "@/api/system/order";
import { listProduct } from "@/api/system/product";
import orderDemand from "@/views/system/orderDemand";

export default {
  name: "OrderReview",
  components: {
    orderDemand
  },
  dicts: ['ices_order_status_review'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
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
      // 订单表格数据
      orderList: [],
      // 描述时间范围
      daterangeOrDeadline: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        maCode: undefined,
        clCode: undefined,
        orName: undefined,
        orStat: undefined,
        orPriority: undefined,
        orDeadline: undefined,
        orDelete: 0,
      },
      // 产品列表
      productList: [],
      // 查看详情相关
      viewOpen: false,
      viewData: {},
      // 1-根据产品管理
      mode: 0,
      // 页面顶部提示
      hint: '',
      // 表单数据
      form: {}
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.maCode) {
      this.mode = 1
    }
    await this.getProductList();
    this.getList();
  },
  async created() {
    if (this.$route.query.maCode) {
      this.mode = 1
    } else {
      this.mode = 0
    }
    await this.getProductList();
    this.getList();
  },
  methods: {  
    // 开始审核
    startReview(row) {
      this.$modal.confirm('是否要开始审核？').then(() => {
        this.loading = true;
        getOrder(row.orId).then(response => {
          this.form = response.data;
          if (this.form.orStat === '2') this.form.orStat = '3';
          if (this.form.orStat === '8') this.form.orStat = '9';
          else this.form.orStat = 'c';
          updateOrder(this.form).then(response => {
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
        getOrder(row.orId).then(response => {
          this.form = response.data;
          if (this.form.orStat === '3') {
            // 新增审核
            this.form.orStat = '4';
          } else if (this.form.orStat === 'c') {
            // 弃用审核
            this.form.orStat = 'a';
          } else if (this.form.orStat === '9') {
            // 修改审核
            this.form.orStat = '4';
          }
          // 原订单改为弃用
          if (row.orCodeOrgn) {
            listOrder({ orCode: row.orCodeOrgn }).then(response => {
              const order = response.rows[0]
              if (order) {
                order.orStat = 'a'
                updateOrder(order)
              }
            })
          }
          // 新订单改为对应状态
          updateOrder(this.form).then(response => {
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
        getOrder(row.orId).then(response => {
          this.form = response.data;
          if (this.form.orStat === '3' || this.form.orStat === '9') {
            // 新增审核、修改审核
            this.form.orStat = '1';
          } else if (this.form.orStat === 'c') {
            // 弃用审核
            this.form.orStat = '4';
          }
          updateOrder(this.form).then(response => {
            this.$modal.msgSuccess("已驳回审核");
            this.getList();
          });
        });
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    
    // 查询产品列表
    getProductList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listProduct().then(response => {
          this.productList = response.rows || []
          if (this.mode === 1) {
            let product = this.productList.find(ele => ele.maCode === this.$route.query.maCode)
            // 构造提示文本
            this.hint = "产品 "
            this.hint += product.maName
            this.hint += " "
            // 设置筛选
            this.queryParams.maCode = product.maCode
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false;
        })
      })
    },
    
    /** 查询订单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeOrDeadline && '' != this.daterangeOrDeadline) {
        this.queryParams.params["beginOrDeadline"] = this.daterangeOrDeadline[0];
        this.queryParams.params["endOrDeadline"] = this.daterangeOrDeadline[1];
      }
      listReviewOrder(this.queryParams).then(response => {
        this.orderList = response.rows || [];
        this.total = response.total || 0;
        this.loading = false;
      }).catch(error => {
        console.error("获取订单列表失败:", error);
        this.orderList = [];
        this.total = 0;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.idSelect = undefined
      this.codeSelect = undefined
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeOrDeadline = [];
      this.resetForm("queryForm");
      this.idSelect = undefined
      this.codeSelect = undefined
      this.queryParams.maCode = this.$route.query.maCode
      this.handleQuery();
    },
    // 选中数据条目
    handleCurrentChange(current, old) {
      this.idSelect = current.orId
      this.codeSelect = current.orCode
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    },
    /** 查看详情按钮操作 */
    handleView(row) {
      this.loading = true;
      getOrder(row.orId).then(response => {
        this.loading = false;
        this.viewData = response.data || {};
        this.viewOpen = true;
      }).catch(() => {
        this.loading = false;
        this.$modal.msgError("获取订单详情失败");
      });
    },
    // 根据物料（产品）的编码，解析得到名称
    getProductName(maCode) {
      if (!maCode) return '';
      if (!this.productList || this.productList.length === 0) return maCode;
      
      const product = this.productList.find(ele => ele.maCode === maCode);
      return product ? product.maName : maCode;
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
.view-card {
  max-height: 50vh;
  overflow: scroll;
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