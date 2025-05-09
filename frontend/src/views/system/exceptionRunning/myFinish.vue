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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleLog(scope.row)"
          >处理日志</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="处理日志" :visible.sync="open" width="40%" append-to-body>
      <el-descriptions border :column="1">
        <el-descriptions-item label="处理人">
          {{ parseUserId(logData.exrlUserHandle) }}
        </el-descriptions-item>
        <el-descriptions-item label="处理时间">
          {{ logData.exrlTime }}
        </el-descriptions-item>
        <el-descriptions-item label="处理意见">
          {{ logData.exrlResult }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import { listFinishProcess } from '@/api/system/exceptionRunning';
import { listExceptionRecordLog } from "@/api/system/exceptionRecordLog";
import { listUser } from "@/api/system/user";
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
      jumpRange: [],
      // 要查看的处理日志信息
      logData: {
        exrlUserHandle: '',
        exrlTime: '',
        exrlResult: ''
      },
      // 用户列表
      userList: []
    };
  },
  async created() {
    await this.getList()
    await this.getUserList()
  },
  async activated() {
    await this.getList()
    await this.getUserList()
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
    // 获取用户列表
    getUserList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listUser().then(response => {
          this.userList = response.rows
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
    /**
     * 查看处理日志
     * @param {any} row 任务信息
     * @author YangZY
     * @date 20250509
     */
    handleLog(row) {
      listExceptionRecordLog({ exrlTask: row.taskId }).then(response => {
        if (response.rows.length > 0) {
          this.logData = response.rows[0]
          this.open = true
        }
      })
    },
    /**
     * 处理人ID映射
     * @param {number} userId 用户ID
     * @returns 用户名
     * @author YangZY
     * @date 20250509
     */
    parseUserId(userId) {
      const user = this.userList.find(ele => ele.userId === userId)
      return user? user.userName : ''
    }
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