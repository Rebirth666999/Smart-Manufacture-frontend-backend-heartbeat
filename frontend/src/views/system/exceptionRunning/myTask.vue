<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="todoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="任务编号" align="center" prop="taskId" :show-overflow-tooltip="true"/>
      <el-table-column label="流程名称" align="center" prop="procDefName"/>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleProcess(scope.row)"
          >处理</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <el-dialog title="处理异常" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="处理意见" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item prop="end">
          <span slot="label">
            <el-tooltip placement="top">
              <div slot="content">
                <div>若选择是，则此任务处理完毕后异常即处理完成</div>
                <div>后续任务均无需处理</div>
                <div>该选项可在生命周期中禁用</div>
              </div>
              <i class="el-icon-question"></i>
            </el-tooltip>
            结束流程
          </span>
          <el-switch v-model="form.end" :disabled="disabled.end" active-text="是" inactive-text="否" />
        </el-form-item>
        <!-- <el-form-item prop="jump">
          <span slot="label">
            <el-tooltip placement="top">
              <div slot="content">
                <div>若选择是，则此任务处理完毕后跳转到指定的任务处理</div>
                <div>该选项可在生命周期中禁用</div>
              </div>
              <i class="el-icon-question"></i>
            </el-tooltip>
            跳转处理
          </span>
          <el-switch v-model="form.jump" :disabled="disabled.jump" active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item label="跳转目标" prop="jumpTarget" v-if="form.jump">
          <el-select v-model="form.jumpTarget" placeholder="请选择跳转目标">
            <el-option
              v-for="item in jumpRange"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :disabled="item.disabled">
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="异常源">
          <div> {{ parseExsCode(exceptionRecord.exsCode) }} </div>
        </el-form-item>
        <el-form-item label="异常">
          <div> {{ parseExCode(exceptionRecord.exCode) }} </div>
        </el-form-item>
        <el-form-item label="处理进度">
          <el-image v-if="processView.img" :src="processView.img" />
          <!-- <process-viewer :key="`designer-${processView.index}`" :xml="processView.xmlData" :style="{'height': '300px', 'margin-bottom': '2em'}" /> -->
        </el-form-item>
        <el-form-item label="处理方法">
          <div> {{ currentTask.description || '暂无' }} </div>
        </el-form-item>
        <el-form-item label="异常描述">
          <div> {{ exceptionRecord.exrDesc || '暂无' }} </div>
        </el-form-item>
        <el-form-item label="异常参数">
          <div> {{ exceptionRecord.exrParam || '无' }} </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 认</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTodoProcess, getProcessXml, getProcessFlowXml, handleTask } from '@/api/system/exceptionRunning';
import ProcessViewer from '@/components/ProcessViewer'
import { xml2json } from 'xml-js';
import { pictureClip } from '@/utils/pictureClip';
import { listExceptionRecord } from "@/api/system/exceptionRecord";
import { listException } from "@/api/system/exception";
import { listExceptionSource } from "@/api/system/exceptionSource";

export default {
  name: "TodoException",
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
      todoList: [],
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
      // 异常上报信息
      exceptionRecord: {}
    };
  },
  async created() {
    await this.getExceptionList()
    await this.getExceptionSourceList()
    await this.getList()
    if (this.$route.query.taskId) {
      const task = this.todoList.find(ele => ele.taskId === this.$route.query.taskId)
      this.$router.push(`/todoException`)
      if (task) {
        this.handleProcess(task)
      }
    }
  },
  async activated() {
    await this.getExceptionList()
    await this.getExceptionSourceList()
    await this.getList()
    if (this.$route.query.taskId) {
      const task = this.todoList.find(ele => ele.taskId === this.$route.query.taskId)
      this.$router.push(`/todoException`)
      if (task) {
        this.handleProcess(task)
      }
    }
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
    /** 查询待办任务列表 */
    getList() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        listTodoProcess().then(response => {
          this.todoList = response.data;
          resolve()
        }).catch(() => {
          reject()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    // 跳转到处理页面
    handleProcess(row) {
      this.currentTask = row
      this.processView.index = row.procDefId
      this.open = true
      // 填充处理进度图片
      getProcessFlowXml(row.procInsId).then(response => {
        pictureClip(response, "img/png").then(res => {
          this.processView.img = res
        })
      })
      // 读取流程图源文件
      getProcessXml(row.procDefId).then(response => {
        this.processView.xmlData = response.data
        // 读取流程图
        const process = (JSON.parse(xml2json(response.data))).elements[0].elements[0]
        const currentNode = process.elements.find(ele => ele.attributes.id === row.taskDefKey	)
        const allNodes = process.elements.filter(ele => ele.name === "bpmn2:userTask")
        // 是否禁用结束和跳转
        const code = currentNode.attributes['flowable:text']
        this.disabled.end = code.startsWith("0")
        this.disabled.jump = code.endsWith("0")
        // 跳转的范围选择
        this.jumpRange = []
        allNodes.forEach(ele => {
          this.jumpRange.push({
            label: ele.attributes.name,
            value: ele.attributes.id,
            disabled: ele.attributes.id === row.taskDefKey
          })
        })
      })
      // 读取上报信息
      listExceptionRecord({ exrProcess: row.procInsId }).then(response => {
        if (response.rows.length > 0) {
          this.exceptionRecord = response.rows[0]
        } else {
          this.exceptionRecord = {}
        }
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        content: undefined,
        end: false,
        jump: false,
        target: undefined
      };
      this.resetForm("form");
    },
    // 提交处理
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          handleTask({ ...this.form, taskId: this.currentTask.taskId }).then(response => {
            this.$modal.msgSuccess("处理成功");
            this.open = false;
            this.getList();
          }).finally(() => {
            this.buttonLoading = false;
          });
        }
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
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
.el-select{
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>