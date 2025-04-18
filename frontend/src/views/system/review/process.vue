<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="目标产品" prop="maCode">
        <el-select
          v-model="queryParams.maCode"
          placeholder="请选择目标产品"
          clearable
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
      <el-table-column label="工艺流程编码" align="center" prop="procCode" />
      <el-table-column label="目标产品" align="center" prop="maCode">
        <template slot-scope="scope">
          {{ productList.find(ele => ele.maCode === scope.row.maCode).maName || '' }}
        </template>
      </el-table-column>
      <el-table-column label="工艺流程名称" align="center" prop="procName" />
      <el-table-column label="工艺流程状态" align="center" prop="procStat">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ices_process_status_review" :value="scope.row.procStat"/>
        </template>
      </el-table-column>
     
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
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
            @click="startReview(scope.row)"
          >开始审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            v-show="scope.row.procStat === '3' || scope.row.procStat === '8'"
            @click="passReview(scope.row)"
          >通过审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            v-show="scope.row.procStat === '3' || scope.row.procStat === '8'"
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
import { getProcess, listProcess, getBpmnXml, updateProcess ,listReviewProcess} from "@/api/system/process";
import { listProduct } from "@/api/system/product";
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
        prCode: undefined,
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
      // 表单数据
      form: {}
    };
  },
  async created() {
    await this.getProductList();
    this.getList();
  },
  async activated() {
    await this.getProductList();
    this.getList();
  },
  methods: {
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
    /** 查询工艺流程列表 */
    getList() {
      this.loading = true;
      listReviewProcess(this.queryParams).then(response => {
        this.processList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 开始审核
    startReview(row) {
      this.$modal.confirm('是否要开始审核？').then(() => {
        this.loading = true;
        getProcess(row.procId).then(response => {
          this.form = response.data;
          if (this.form.procStat === '2') this.form.procStat = '3';
          else this.form.procStat = '8';
        updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已开始审核");
            this.getList();
          })
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
        getProcess(row.procId).then(response => {
          this.form = response.data;
          if (this.form.procStat === '3' || this.form.procStat === '8') this.form.procStat = '4';
        updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已通过审核");
            this.getList();
          })
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
        getProcess(row.procId).then(response => {
          this.form = response.data;
          if (this.form.procStat === '3' || this.form.procStat === '8') this.form.procStat = '1';
        updateProcess(this.form).then(response => {
            this.$modal.msgSuccess("已驳回审核");
            this.getList();
          })
        });
      }).catch(() => {
      }).finally(() => {
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