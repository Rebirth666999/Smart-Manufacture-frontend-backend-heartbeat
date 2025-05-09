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
            <el-switch v-model="form.end" :disabled="disabled.end" active-text="是" inactive-text="否" />
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
          </el-form-item>
        </el-form>
        <!-- <process-viewer :key="`designer-${processView.index}`" :xml="processView.xmlData" :style="{'height': '300px', 'margin-bottom': '2em'}" /> -->
        <div>
          <el-image v-if="processView.img" :src="processView.img" />
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
      const that = this
      getProcessFlowXml(row.procInsId).then(response => {
        const blob = new Blob([response], { type: 'image/png' })
        const url = window.URL.createObjectURL(blob)
        // 渲染图片得到图片信息
        const canvas = document.createElement("canvas")
        const ctx = canvas.getContext("2d")
        const image = new Image()
        image.src = url
        image.onload = function () {
          canvas.width = image.width
          canvas.height = image.height
          ctx.drawImage(image, 0, 0, canvas.width, canvas.height)
          const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height)
          const { data, width, height } = imageData;

          let startX = width
          let startY = height
          let endX = 0
          let endY = 0
          // 像素遍历
          for (let col = 0; col < width; col++) {
            for (let row = 0; row < height; row++) {
              const pxStartIndex = (row * width + col) * 4;
              const pxData = {
                r: data[pxStartIndex],
                g: data[pxStartIndex + 1],
                b: data[pxStartIndex + 2],
                a: data[pxStartIndex + 3]
              };
              // 不透明就是有颜色
              const colorExist = pxData.a !== 0;
              if (colorExist) {
                startX = Math.min(col, startX);
                endX = Math.max(col, startX);
                startY = Math.min(row, startY);
                endY = Math.max(row, endY);
              }
            }
          }
          // 追加边距
          endX += 11;
          endY += 11;
          startX -= 10;
          startY -= 10;
          // 渲染新的图片
          const cropCanvas = document.createElement("canvas");
          const cropCtx = cropCanvas.getContext("2d");
          cropCanvas.width = endX - startX;
          cropCanvas.height = endY - startY;
          cropCtx.drawImage(
            image,
            startX,
            startY,
            cropCanvas.width,
            cropCanvas.height,
            0,
            0,
            cropCanvas.width,
            cropCanvas.height
          );
          // 渲染结束返回url
          that.processView.img = cropCanvas.toDataURL()
        }
      })
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
<style scoped>
.el-select{
  width: 100%;
}
.el-date-editor{
  width: 100%;
}
</style>