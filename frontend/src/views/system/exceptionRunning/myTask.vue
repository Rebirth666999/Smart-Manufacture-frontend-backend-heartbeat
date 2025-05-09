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
      <el-dialog title="处理异常" :visible.sync="open" width="70%" append-to-body>
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
            <el-switch v-model="form.end" active-text="是" inactive-text="否" />
          </el-form-item>
          <el-form-item prop="jump">
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
            <el-switch v-model="form.jump" active-text="是" inactive-text="否" />
          </el-form-item>
          <el-form-item label="跳转目标" prop="jumpTarget">
            <el-input v-model="form.jumpTarget" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-form>
        <!-- <process-viewer :key="`designer-${processView.index}`" :xml="processView.xmlData" :style="{'height': '300px', 'margin-bottom': '2em'}" /> -->
        <div>
          <img v-if="processView.img" :src="processView.img" />
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 认</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </el-table>
  </div>
</template>

<script>
import { listTodoProcess, getProcessXml, getProcessFlowXml } from '@/api/system/exceptionRunning';
import ProcessViewer from '@/components/ProcessViewer'
import { xml2json } from 'xml-js';

export default {
  name: "Todo",
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
      rules: {},
      // 当前选中的任务
      currentTask: {},
      processView: {
        index: '',
        xmlData: '',
        img: undefined
      }
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
      listTodoProcess().then(response => {
        this.todoList = response.data;
        this.loading = false;
      });
    },
    // 跳转到处理页面
    handleProcess(row) {
      this.currentTask = row
      this.processView.index = row.procDefId
      this.open = true
      getProcessFlowXml(row.procInsId).then(response => {
        const blob = new Blob([response], { type: 'image/png' })
        this.processView.img = window.URL.createObjectURL(blob)
      })
      getProcessXml(row.procDefId).then(response => {
        this.processView.xmlData = response.data
        // 读取流程图
        const process = (JSON.parse(xml2json(response.data))).elements[0].elements[0]
        const currentNode = process.elements.find(ele => ele.attributes.id === row.taskDefKey	)
        const allNodes = process.elements.filter(ele => ele.name === "bpmn2:userTask")
        console.log(currentNode, allNodes)
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
