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
        <el-tab-pane label="上报记录" name="report">
          <el-descriptions border v-if="idSelect">
            <el-descriptions-item label="上报记录ID">
              {{ exceptionRecord.exrId || '' }}
            </el-descriptions-item>
            <el-descriptions-item label="上报记录编码">
              {{ exceptionRecord.exrCode || '' }}
            </el-descriptions-item>
            <el-descriptions-item label="异常源">
              {{ parseExsCode(exceptionRecord.exsCode) }}
            </el-descriptions-item>
            <el-descriptions-item label="异常">
              {{ parseExCode(exceptionRecord.exCode) }}
            </el-descriptions-item>
            <el-descriptions-item label="描述">
              {{ exceptionRecord.exrDesc || '' }}
            </el-descriptions-item>
            <el-descriptions-item label="参数">
              {{ exceptionRecord.exrParam || '' }}
            </el-descriptions-item>
            <el-descriptions-item label="状态">
              <dict-tag :options="dict.type.ices_exception_record_status" :value="exceptionRecord.exrStat"/>
            </el-descriptions-item>
            <el-descriptions-item label="等级">
              <dict-tag :options="dict.type.ices_exception_record_level" :value="exceptionRecord.exrLevel"/>
            </el-descriptions-item>
            <el-descriptions-item label="上报类型">
              <el-tag type="warning" v-if="exceptionRecord.exrUserReport === '信息系统'">自动上报</el-tag>
              <el-tag type="info" v-else>人工上报</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="上报人">
              {{ exceptionRecord.exrUserReport || '' }}
            </el-descriptions-item>
            <el-descriptions-item label="上报时间">
              {{ exceptionRecord.exrCdate || '' }}
            </el-descriptions-item>
            <el-descriptions-item label="影响因子">
              {{ exceptionRecord.exrImpactFactor || '' }}
            </el-descriptions-item>
            <el-descriptions-item label="影响等级">
              <dict-tag :options="dict.type.ices_exception_record_impact_level" :value="exceptionRecord.exrImpactLevel"/>
            </el-descriptions-item>
          </el-descriptions>
          <el-empty v-else description="选中异常记录后即可查看上报记录" />
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
import ExceptionRecordLog from '@/views/system/exceptionRecordLog';
import { listExceptionRecord } from "@/api/system/exceptionRecord";
import { listException } from "@/api/system/exception";
import { listExceptionSource } from "@/api/system/exceptionSource";

export default {
  name: "ExceptionProcess",
  dicts: ['wf_process_status', 'ices_exception_record_status', 'ices_exception_record_level', 'ices_exception_record_impact_level'],
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
      // 上报记录
      exceptionRecord: {},
      // 异常源列表
      exceptionSourceList: [],
      // 异常列表
      exceptionList: []
    };
  },
  async created() {
    await this.getExceptionList()
    await this.getExceptionSourceList()
    this.getList()
  },
  async activated() {
    await this.getExceptionList()
    await this.getExceptionSourceList()
    this.getList()
  },
  methods: {
    // 获取异常源列表
    getExceptionSourceList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listExceptionSource().then(response => {
          this.exceptionSourceList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 获取异常列表
    getExceptionList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listException().then(response => {
          this.exceptionList = response.rows
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      listProcess().then(response => {
        this.processList = response.data;
        this.loading = false;
      });
    },
    /** 查询异常记录列表 */
    getExceptionRecordList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listExceptionRecord({ exrProcess: this.idSelect }).then(response => {
          if (response.rows.length > 0) {
            this.exceptionRecord = response.rows[0]
          } else {
            this.exceptionRecord = {}
          }
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
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
        this.getExceptionRecordList()
      }
    },
    categoryFormat(row, column) {
      return this.categoryOptions.find(k => k.code === row.category)?.categoryName ?? '';
    },
    // 解析异常源
    parseExsCode(exsCode) {
      if (exsCode) {
        const exs = this.exceptionSourceList.find(ele => ele.exsCode = exsCode)
        if (exs) return exs.exsName
      }
      return ''
    },
    // 解析异常
    parseExCode(exCode) {
      if (exCode) {
        const ex = this.exceptionList.find(ele => ele.exCode = exCode)
        if (ex) return ex.exName
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