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

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所需产品" prop="prId">
        <el-select
          v-model="queryParams.prId"
          placeholder="请选择产品"
          clearable
          :disabled="mode === 1"
        >
          <el-option
            v-for="item in productList"
            :key="item.prId"
            :label="item.prName"
            :value="item.prId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="客户" prop="clId">
        <el-input
          v-model="queryParams.clId"
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

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="orId" v-if="true"/>
      <el-table-column label="所需产品" align="center" prop="prId">
        <template slot-scope="scope">
          {{ productList.find(ele => ele.prId === scope.row.prId).prName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="客户" align="center" prop="clId" />
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
            v-show="scope.row.orStat === '2'"
          >审核</el-button>
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
    <el-dialog :title="'查看订单详情 - ' + viewData.orName" :visible.sync="viewOpen" width="530px" append-to-body>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="订单ID">{{ viewData.orId }}</el-descriptions-item>
        <el-descriptions-item label="所需产品">
          {{ productList.find(ele => ele.prId === viewData.prId).prName || viewData.prId }}
        </el-descriptions-item>
        <el-descriptions-item label="客户">{{ viewData.clId }}</el-descriptions-item>
        <el-descriptions-item label="订单名称">{{ viewData.orName }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="dict.type.ices_order_status_review" :value="viewData.orStat"/>
        </el-descriptions-item>
        <el-descriptions-item label="所需产品数量">{{ viewData.orDemand }}</el-descriptions-item>
        <el-descriptions-item label="订单优先级">{{ viewData.orPriority }}</el-descriptions-item>
        <el-descriptions-item label="截止时间">
          {{ parseTime(viewData.orDeadline, '{y}-{m}-{d}') }}
        </el-descriptions-item>
        <el-descriptions-item label="总价">{{ viewData.orPrice }}</el-descriptions-item>
        <el-descriptions-item label="描述">{{ viewData.orDesc }}</el-descriptions-item>
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
import { getOrder, listReviewOrder } from "@/api/system/order";
// import { listProduct } from "@/api/system/product";

export default {
  name: "OrderReview",
  dicts: ['ices_order_status_review'],
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
      // 订单表格数据
      orderList: [],
      // 描述时间范围
      daterangeOrDeadline: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prId: this.$route.query.prId,
        clId: undefined,
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
      hint: ''
    };
  },
  async created() {
    // 检查来源
    if (this.$route.query.prId) {
      this.mode = 1
    }
    await this.getProductList();
    this.getList();
  },
  methods: {
    // 查询产品列表
    getProductList() {
      listProduct().then(response => {
        this.productList = response.rows;
        if (this.mode === 1) {
          this.hint = "产品 "
          this.hint += response.rows.find(ele => ele.prId === this.$route.query.prId).prName
          this.hint += " "
        }
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
        this.orderList = response.rows;
        this.total = response.total;
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
      this.daterangeOrDeadline = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orId)
      this.single = selection.length!==1
      this.multiple = !selection.length
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
        this.viewData = response.data;
        this.viewOpen = true;
      });
    }
  }
};
</script> 