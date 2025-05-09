<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="finishList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="任务编号" align="center" prop="taskId" :show-overflow-tooltip="true"/>
      <el-table-column label="流程名称" align="center" prop="procDefName" :show-overflow-tooltip="true"/>
      <el-table-column label="异常名称" align="center" prop="exName"/>
      <el-table-column label="任务节点" align="center" prop="taskName"/>
      <el-table-column label="流程版本" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">{{scope.row.exlvName}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="流程发起人" align="center">
        <template slot-scope="scope">
          <label>{{scope.row.startUserName}}</label>
        </template>
      </el-table-column>
      <el-table-column label="接收时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="结束时间" align="center" prop="finishTime" width="180"/>
      <el-table-column label="耗时" align="center" prop="duration" />
    </el-table>
  </div>
</template>

<script>
import { listFinishProcess } from '@/api/system/exceptionRunning';
import ProcessViewer from '@/components/ProcessViewer'

export default {
  name: "MyFinish",
  components: {
    ProcessViewer
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 按钮加载
      buttonLoading: false,
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
      // 流程待办任务表格数据
      finishList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        category: null
      },
      // 表单参数
      form: {
        end: false,
        jump: false
      },
      // 表单校验
      rules: {
        content: [
          { required: true, message: "处理意见不能为空", trigger: "blur" }
        ],
        end: [
          { required: true, message: "请选择是否结束流程", trigger: "blur" }
        ],
        jump: [
          { required: true, message: "请选择是否跳转处理", trigger: "blur" }
        ],
        jumpTarget: [
          { required: true, message: "跳转目标不能为空", trigger: "blur" }
        ],
      },
      // 当前选中的任务
      currentTask: {},
      // 查看器参数
      processView: {
        index: '',
        xmlData: '',
        img: undefined
      },
      // 是否禁用跳转和完成
      disabled: {
        end: false,
        jump: false
      },
      // 跳转流程的范围
      jumpRange: []
    };
  },
  async created() {
    await this.getList()
  },
  async activated() {
    await this.getList()
  },
  methods: {
    /** 查询待办任务列表 */
    getList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listFinishProcess().then(response => {
          this.finishList = response.data;
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
  }
};
</script>
<style scoped>
.el-select{
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>