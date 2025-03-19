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
            <el-button size="mini" type="text" @click="openParamsForm(row, $index)">查看</el-button>
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
            {{ editParam.eospaIdParent || 0 }}
          </el-descriptions-item>
          <el-descriptions-item label="参数类型">
            <dict-tag :options="dict.type.ices_param_type" :value="editParam.eospaType"/>
          </el-descriptions-item>
          <el-descriptions-item label="参数位置">
            <dict-tag :options="dict.type.ices_param_position" :value="editParam.eospaPos"/>
          </el-descriptions-item>
          <el-descriptions-item label="默认值">
            {{ editParam.eospaValue || 0 }}
          </el-descriptions-item>
        </el-descriptions>
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
        eospaIdParent: 0,
        eospaType: '',
        eospaPos: '',
        eospaValue: ''
      }
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
        const eosListCurrent = this.eosList.filter(ele => ele.eoId === currentTask.eoId && ele.eaoId)
        for (let it of eosListCurrent) {
          this.paramList = this.paramList.concat(this.eospaList.filter(ele => ele.eosId === it.eosId))
        }
      }
    },
    // 更新属性：设备
    updateEquipment() {
      this.eoId = ''
      this.$emit('updateTask', { id: this.current.id, eqId: this.eqId, eoId: '' })
    },
    // 更新属性：设备操作
    updateEquipmentOperation() {
      this.$emit('updateTask', { id: this.current.id, eqId: this.eqId, eoId: this.eoId })
    },
    // 打开新增/修改参数窗口
    openParamsForm(attr, index) {
      this.editParam = this.paramList[index]
      this.propertyFormModelVisible = true;
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
