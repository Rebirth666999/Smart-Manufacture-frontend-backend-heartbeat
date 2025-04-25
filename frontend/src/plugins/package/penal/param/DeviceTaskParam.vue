<template>
  <div class="panel-tab__content">
    <el-form class="penal-form" label-width="70px">
      <!-- 显示参数表格 -->
      <el-table :data="paramList" size="mini" max-height="240" border fit>
        <el-table-column label="名称" prop="eospaName" show-overflow-tooltip />
        <el-table-column label="类型" prop="eospaType" show-overflow-tooltip>
          <template slot-scope="scope">
            <dict-tag :options="dict.type.ices_param_type" :value="scope.row.eospaType"/>
          </template>
        </el-table-column>
        <el-table-column label="位置" prop="eospaPos" show-overflow-tooltip>
          <template slot-scope="scope">
            <dict-tag :options="dict.type.ices_param_position" :value="scope.row.eospaPos"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="90px">
          <template slot-scope="{ row, $index }">
            <el-button size="mini" type="text" @click="openParamsForm(row, $index)">配置</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 查看参数窗口 -->
      <el-dialog :visible.sync="propertyFormModelVisible" title="参数配置" width="600px" append-to-body destroy-on-close>
        <el-descriptions border :column="1">
          <el-descriptions-item label="参数名称">
            {{ editParam.eospaName || '' }}
          </el-descriptions-item>
          <el-descriptions-item label="父级参数序号">
            {{ editParam.eospaCodeParent || '' }}
          </el-descriptions-item>
          <el-descriptions-item label="参数类型">
            <dict-tag :options="dict.type.ices_param_type" :value="editParam.eospaType"/>
          </el-descriptions-item>
          <el-descriptions-item label="参数位置">
            <dict-tag :options="dict.type.ices_param_position" :value="editParam.eospaPos"/>
          </el-descriptions-item>
          <el-descriptions-item label="默认值">
            {{ editParam.eospaValue || '' }}
          </el-descriptions-item>
        </el-descriptions>
        <el-form>
          <el-form-item label="参数值">
            <el-input v-model="dtpaValue" type="textarea" placeholder="若留空则使用默认参数" />
          </el-form-item>
        </el-form>
        <template slot="footer">
          <el-button @click="propertyFormModelVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitParam">提 交</el-button>
        </template>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "DeviceTask",
  dicts: ['ices_param_type', 'ices_param_position'],
  props: {
    element: Object,
    eosList: Array,
    eospaList: Array,
    taskList: Array
  },
  data() {
    return {
      current: null,
      attrs: {},
      paramList: [],
      // 是否显示新增/修改窗口
      propertyFormModelVisible: false,
      // 查看参数的属性
      editParam: {
        eospaName: '',
        eospaCodeParent: 0,
        eospaType: '',
        eospaPos: '',
        eospaValue: ''
      },
      // 正在编辑的taskList项目
      task: undefined,
      // 待填写参数
      dtpaValue: ''
    };
  },
  watch: {
    // 切换选择的对象时，获取信息
    element: {
      immediate: true,
      handler: function(element) {
        this.current = element
        this.attrs = element.businessObject.$attrs
        this.paramList = []
        this.updateData()
      }
    },
    taskList: {
      immediate: true,
      handler: function(taskList) {
        this.paramList = []
        this.updateData()
      }
    }
  },
  methods: {
    // 更新组件内容
    updateData() {
      const currentTask = this.taskList.find(ele => ele.id === this.current.id)
      if (currentTask) {
        this.task = currentTask
        const eosListCurrent = this.eosList.filter(ele => ele.eoCode === currentTask.eoCode && ele.eaoCode)
        for (let it of eosListCurrent) {
          this.paramList = this.paramList.concat(this.eospaList.filter(ele => ele.eosCode === it.eosCode))
        }
      }
    },
    // 打开新增/修改参数窗口
    openParamsForm(attr, index) {
      const param = this.task.param.find(ele => ele.eospaCode === attr.eospaCode)
      this.editParam = this.paramList[index]
      this.dtpaValue = param ? param.dtpaValue : ''
      this.propertyFormModelVisible = true;
    },
    // 提交参数值
    submitParam() {
      if (this.dtpaValue.length === 0 && !this.editParam.eospaValue) {
        this.$modal.msgWarning("请填写参数值")
      } else {
        // 填充默认值
        if (this.dtpaValue.length === 0) this.dtpaValue = this.editParam.eospaValue
        // 保存参数
        const idx = this.task.param.findIndex(ele => ele.eospaCode === this.editParam.eospaCode)
        if (idx === -1) {
          this.task.param.push({ eospaCode: this.editParam.eospaCode, dtpaValue: this.dtpaValue })
        } else {
          this.task.param[idx] = { eospaCode: this.editParam.eospaCode, dtpaValue: this.dtpaValue }
        }
        this.$emit('updateTask', this.task)
        this.propertyFormModelVisible = false
        this.$modal.msgSuccess("已配置参数")
      }
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>
<style scoped>
.penal-form {
  padding: 0 8px;
}
</style>
