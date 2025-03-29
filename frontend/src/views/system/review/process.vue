<template>
  <div class="app-container">
    <!-- 顶部提示 -->
    <el-alert
      v-show="hint.length > 0"
      :title="`正在根据${hint}筛选工艺流程`"
      type="info"
      show-icon
      :closable="false"
      class="mb8"
    >
    </el-alert>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="目标产品" prop="prId">
        <el-select
          v-model="queryParams.prId"
          placeholder="请选择目标产品"
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
      <el-form-item label="名称" prop="procName">
        <el-input
          v-model="queryParams.procName"
          placeholder="请输入工艺流程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="procStat">
        <el-select v-model="queryParams.procStat" placeholder="请选择工艺流程状态" clearable>
          <el-option
            v-for="dict in dict.type.ices_process_status_review"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['system:process:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工艺流程ID" align="center" prop="procId" v-if="true"/>
      <el-table-column label="目标产品" align="center" prop="prId">
        <template slot-scope="scope">
          {{ productList.find(ele => ele.prId === scope.row.prId).prName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="工艺流程名称" align="center" prop="procName" />
      <el-table-column label="工艺流程状态" align="center" prop="procStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_process_status_review" :value="scope.row.procStat"/>
        </template>
      </el-table-column>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-box"
            @click="handleProcessMaterialView(scope.row)"
          >原料需求</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="handleViewer(scope.row)"
          >查看流程</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-show="scope.row.procStat === '2' || scope.row.procStat === '7'"
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

    <!-- 查看流程对话框 -->
    <el-dialog :title="viewerData.title" :visible.sync="viewerOpen" width="70%" append-to-body>
      <process-viewer
        v-loading="viewerData.loading"
        :key="`designer-${viewerData.index}`"
        :xml="viewerData.bpmnXml"
        :style="{height: '60vh'}"
      />
    </el-dialog>
  </div>
</template>

<script>
import { getProcess, listReviewProcess, getBpmnXml } from "@/api/system/process";
// import { listProduct } from "@/api/system/product";
import ProcessViewer from '@/components/ProcessViewer';

export default {
  name: "ProcessReview",
  components: {
    ProcessViewer,
  },
  dicts: ['ices_process_status_review'],
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
      // 工艺流程表格数据
      processList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prId: this.$route.query.prId,
        procName: undefined,
        procStat: undefined,
        procDelete: 0,
      },
      // 产品列表
      productList: [],
      // 查看窗口是否打开
      viewerOpen: false,
      // 查看器相关数据
      viewerData: {
        title: '',
        loading: false,
        index: undefined,
        bpmnXml: ''
      },
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
    /** 查询工艺流程列表 */
    getList() {
      this.loading = true;
      listReviewProcess(this.queryParams).then(response => {
        this.processList = response.rows;
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.procId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/process/export', {
        ...this.queryParams
      }, `process_${new Date().getTime()}.xlsx`)
    },
    // 查看工艺流程的原料需求
    handleProcessMaterialView(row) {
      this.$router.push(`/processFlow/processMaterial?procId=${row.procId}`)
    },
    /** 查看流程按钮操作 */
    handleViewer(row) {
      this.viewerData.loading = true
      this.viewerData.title = row.procName
      this.viewerData.index = row.procModel
      this.viewerOpen = true
      getBpmnXml(row.procModel).then(response => {
        this.viewerData.bpmnXml = response.data || ''
        this.viewerData.loading = false
      })
    }
  }
};
</script> 