<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header">
        <div class="card-header">
          <div>异常记录信息</div>
        </div>
      </div>
      <div>
        <el-table
          v-loading="loading"
          :data="processList"
          @current-change="handleCurrentChange"
          highlight-current-row
        >
          <el-table-column label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio :value="scope.row.procInsId === idSelect" :label="true" />
            </template>
          </el-table-column>
          <el-table-column label="流程编号" align="center" prop="procInsId" :show-overflow-tooltip="true"/>
          <el-table-column label="流程名称" align="center" prop="procDefName" :show-overflow-tooltip="true"/>
          <el-table-column label="流程定义编号" align="center" prop="procDefId" :show-overflow-tooltip="true"/>
          <el-table-column label="异常名称" align="center" prop="exName" />
          <!-- <el-table-column label="流程类别" align="center" prop="category"     :formatter="categoryFormat" /> -->
          <el-table-column label="流程版本" align="center" width="80px">
            <template slot-scope="scope">
              <el-tag size="medium" >{{ scope.row.exlvName }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="当前节点" align="center" prop="taskName"/>
          <el-table-column label="提交时间" align="center" prop="createTime" width="180"/>
          <el-table-column label="结束时间" align="center" prop="finishTime" width="180"/>
          <el-table-column label="流程状态" align="center" width="100">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.wf_process_status" :value="scope.row.processStatus"/>
            </template>
          </el-table-column>
          <el-table-column label="耗时" align="center" prop="duration" width="180"/>
        </el-table>
      </div>
    </el-card>

    <el-card shadow="never" class="controlled-card">
      <div slot="header">
        <div class="card-header">
          <div>异常记录详情</div>
        </div>
      </div>
      <el-tabs value="task">
        <el-tab-pane label="处理进度" name="task">
          <el-image v-if="idSelect && processView.img" :src="processView.img" />
          <el-empty v-else description="选中异常记录后即可查看处理进度" />
        </el-tab-pane>
        <el-tab-pane label="处理日志" name="log">
          <exception-record-log v-if='idSelect' :key="idSelect" :procInsId="idSelect" />
          <el-empty v-else description="选中异常记录后即可查看处理日志" />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import { listProcess, getProcessFlowXml } from '@/api/system/exceptionRunning';
import { pictureClip } from '@/utils/pictureClip';
import ExceptionRecordLog from '@/views/system/exceptionRecordLog'

export default {
  name: "exceptionRunningProcess",
  dicts: ['wf_process_status'],
  components: {
    ExceptionRecordLog
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      processLoading: true,
      // 选中内容
      idSelect: undefined,
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
      rules: {},
      // 查看器参数
      processView: {
        open: false,
        img: undefined
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
    // 选中数据条目
    handleCurrentChange(current, old) {
      if (current) {
        this.idSelect = current.procInsId
        getProcessFlowXml(current.procInsId).then(response => {
          pictureClip(response, "img/png").then(res => {
            this.processView.img = res
          })
        })
      }
    },
    categoryFormat(row, column) {
      return this.categoryOptions.find(k => k.code === row.category)?.categoryName ?? '';
    },
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
</style>