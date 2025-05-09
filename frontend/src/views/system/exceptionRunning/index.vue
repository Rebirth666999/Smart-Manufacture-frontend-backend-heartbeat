<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程编号" align="center" prop="procInsId" :show-overflow-tooltip="true"/>
      <el-table-column label="流程名称" align="center" prop="procDefName" :show-overflow-tooltip="true"/>
      <el-table-column label="流程定义编号" align="center" prop="procDefId" :show-overflow-tooltip="true"/>
      <el-table-column label="所属异常名称" align="center" prop="exName" :show-overflow-tooltip="true"/>
      <!-- <el-table-column label="流程类别" align="center" prop="category" :formatter="categoryFormat" /> -->
      <el-table-column label="流程版本" align="center" width="80px">
        <template slot-scope="scope">
          <el-tag size="medium" >{{ scope.row.exlvName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="当前节点" align="center" prop="taskName"/>
      <el-table-column label="提交时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="流程状态" align="center" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wf_process_status" :value="scope.row.processStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="耗时" align="center" prop="duration" width="180"/>
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="mini"
            icon="el-icon-tickets"
            @click="handleFlowRecord(scope.row)"
            v-hasPermi="['workflow:process:query']"
          >详情</el-button>
        </template>
      </el-table-column> -->
    </el-table>
  </div>
</template>

<script>
import { listOwnProcess, stopProcess, delProcess } from '@/api/workflow/process';
import { listAllCategory } from '@/api/workflow/category';
import { listProcess } from '@/api/system/exceptionRunning';
export default {
  name: "exceptionRunningProcess",
  dicts: ['wf_process_status'],
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      processLoading: true,
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
      categoryOptions: [],
      processTotal:0,
      // 流程列表数据
      processList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      src: "",
      definitionList:[],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processKey: undefined,
        processName: undefined,
        category: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
    };
  },
  created() {
    this.getList()
  },
  activated() {
    this.getList()
  },
  methods: {
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      listProcess().then(response => {
        this.processList = response.data;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        category: null,
        key: null,
        tenantId: null,
        deployTime: null,
        derivedFrom: null,
        derivedFromRoot: null,
        parentDeploymentId: null,
        engineVersion: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.procInsId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAgain(row) {
      this.$router.push({
        path: '/workflow/process/start/' + row.deployId,
        query: {
          definitionId: row.procDefId,
          procInsId: row.procInsId
        }
      })
      console.log(row);
    },
    /**  取消流程申请 */
    handleStop(row){
      const params = {
        procInsId: row.procInsId
      }
      stopProcess(params).then( res => {
        this.$modal.msgSuccess(res.msg);
        this.getList();
      });
    },
    /** 流程流转记录 */
    handleFlowRecord(row) {
      this.$router.push({
        path: '/workflow/process/detail/' + row.procInsId,
        query: {
          processed: false
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.procInsId || this.ids;
      this.$confirm('是否确认删除流程定义编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delProcess(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('workflow/process/ownExport', {
        ...this.queryParams
      }, `wf_own_process_${new Date().getTime()}.xlsx`)
    },
    categoryFormat(row, column) {
      return this.categoryOptions.find(k => k.code === row.category)?.categoryName ?? '';
    }
  }
};
</script>
